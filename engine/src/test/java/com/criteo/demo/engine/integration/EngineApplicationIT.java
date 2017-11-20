package com.criteo.demo.engine.integration;

import com.criteo.demo.common.model.KafkaProductViewMessage;
import com.criteo.demo.engine.dao.Key;
import com.criteo.demo.engine.dao.ProductViewRepository;
import com.criteo.demo.engine.model.ProductView;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@JsonTest
@ActiveProfiles({ "test" })
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class EngineApplicationIT {

	@Autowired ProductViewRepository productViewRepository;
	@Autowired KafkaTemplate<String, String> kafkaTemplate;
	@Autowired ObjectMapper objectMapper;

	private static final int USER_ID = 5;
	private static final int PRODUCT_ID = 101;

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	@Configuration
	@EnableCassandraRepositories("com.criteo.demo.engine.dao")
	@EnableKafka
	static class CassandraConfig extends AbstractCassandraConfiguration {

		@Override
		public String getKeyspaceName() {
			return "stats";
		}

		@Override
		protected String getContactPoints() {
			return "cassandra";
		}

		@Bean
		public ProducerFactory<String, String> producerFactory() {
			return new DefaultKafkaProducerFactory<>(producerConfigs());
		}

		@Bean
		public Map<String, Object> producerConfigs() {
			Map<String, Object> props = new HashMap<>();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
			props.put(ProducerConfig.RETRIES_CONFIG, 0);
			props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
			props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
			props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			return props;
		}

		@Bean
		public KafkaTemplate<String, String> kafkaTemplate() {
			return new KafkaTemplate<>(producerFactory());
		}

		@Bean
		public CassandraTemplate cassandraTemplate(Session session) {
			return new CassandraTemplate(session);
		}
	}

	@Test
	public void test() throws JsonProcessingException, InterruptedException, ExecutionException, TimeoutException {
		long time = System.currentTimeMillis();
		KafkaProductViewMessage viewProduct = new KafkaProductViewMessage(USER_ID, PRODUCT_ID, time);
		kafkaTemplate.send("view_product", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(viewProduct));


		CompletableFuture<ProductView> result = new CompletableFuture<>();

		Key key = new Key(USER_ID, PRODUCT_ID, new Date(time));
		Runnable task = () -> {

			ProductView one = productViewRepository.findOne(key);
			if (one != null) {
				result.complete(one);
				executor.shutdown();
			}
		};
		executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);

		ProductView productView = result.get(30, TimeUnit.SECONDS);
		assertNotNull(productView);
		assertEquals(USER_ID, (long) productView.getKey().getUserId());
		assertEquals(PRODUCT_ID, (long) productView.getKey().getProductId());
		assertEquals(new Date(time), productView.getKey().getTimestamp());
	}
}
