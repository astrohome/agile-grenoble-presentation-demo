package com.criteo.demo.tracker.integration;

import com.criteo.demo.common.model.KafkaProductViewMessage;
import com.criteo.demo.common.utils.HttpUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AdvertiserTrackerIT {

    private static final int USER_ID = 5;
    private static final int PRODUCT_ID = 101;

    private CompletableFuture<KafkaProductViewMessage> result = new CompletableFuture<>();

    @Configuration
    @EnableKafka
    static class ContextConfiguration {
        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, KafkaProductViewMessage> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, KafkaProductViewMessage> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<String, KafkaProductViewMessage> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                    new StringDeserializer(), new JsonDeserializer<>(KafkaProductViewMessage.class));
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            return props;
        }
    }

    @Test
    public void testKafkaFlux() throws Exception {

        String url = "http://localhost:8080/api/advertiser-tracker/view?userid=" +
                USER_ID + "&productid=" + PRODUCT_ID;

        int send = HttpUtils.send(url);
        assertEquals("Response code should be 200", 200, send);

        KafkaProductViewMessage kafkaMessage = result.get(20, TimeUnit.SECONDS);
        assertNotNull(kafkaMessage);
        assertEquals("Userid should be the same as requested", USER_ID, kafkaMessage.getUserId());
        assertEquals("Productid should be the same as requested", PRODUCT_ID, kafkaMessage.getProductId());
    }

    @KafkaListener(topics = "view_product")
    public void receive(@Payload KafkaProductViewMessage payload) {
        try {
            System.out.println(payload);
            result.complete(payload);
        } catch (Exception ex) {
            result.completeExceptionally(ex);
        }
    }
}