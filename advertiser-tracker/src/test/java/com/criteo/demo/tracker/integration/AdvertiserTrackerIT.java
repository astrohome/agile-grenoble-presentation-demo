package com.criteo.demo.tracker.integration;

import com.criteo.demo.common.model.KafkaProductViewMessage;
import com.criteo.demo.common.utils.HttpUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
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
public class AdvertiserTrackerIT {

    /**
     * Countdown latch
     */
    private CountDownLatch lock = new CountDownLatch(1);

    @Autowired
    private JacksonTester<KafkaProductViewMessage> json;

    private static final int USER_ID = 5;
    private static final int PRODUCT_ID = 101;

    private KafkaProductViewMessage kafkaMessage;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Configuration
    @EnableKafka
    static class ContextConfiguration {
        @Bean
        public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            factory.setBatchListener(true);
            return factory;
        }

        @Bean
        public ConsumerFactory<String, String> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            return props;
        }
    }

    @Test
    public void dummyTest() throws Exception {

        lock.await(5, TimeUnit.SECONDS);

        String url = "http://localhost:8080/api/advertiser-tracker/view?userid=" +
                USER_ID + "&productid=" + PRODUCT_ID;

        int send = HttpUtils.send(url);
        assertEquals("Response code should be 200", 200, send);

        CompletableFuture<Boolean> result = new CompletableFuture<>();

        Runnable task = () -> {
            if (kafkaMessage != null) {
                result.complete(true);
                executor.shutdown();
            }
        };
        executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);

        Boolean gotMessage = result.get(10, TimeUnit.SECONDS);
        if (gotMessage) {
            assertNotNull(this.kafkaMessage);
            assertEquals("Userid should be the same as requested", USER_ID, this.kafkaMessage.getUserId());
            assertEquals("Productid should be the same as requested", PRODUCT_ID, this.kafkaMessage.getProductId());
        }
    }

    @KafkaListener(topics = "view_product")
    public void receive(String payload) throws IOException {
        this.kafkaMessage = this.json.parse(payload).getObject();
    }
}