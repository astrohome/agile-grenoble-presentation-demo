package com.criteo.demo.tracker;

import com.criteo.demo.common.model.KafkaProductViewMessage;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.USER_AGENT;

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

    private KafkaProductViewMessage kafkaMessage;

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
        String url = "http://localhost:8080/api/advertiser-tracker/view?userid=5&productid=101";

        lock.await(5, TimeUnit.SECONDS);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // TODO verify message
        lock.await(20, TimeUnit.SECONDS);
    }



    @KafkaListener(topics = "view_product")
    public void receive(String payload) throws IOException {
        this.kafkaMessage = this.json.parse(payload).getObject();
    }
}
