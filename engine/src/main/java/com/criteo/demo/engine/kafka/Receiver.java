package com.criteo.demo.engine.kafka;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.criteo.demo.common.model.KafkaProductViewMessage;
import com.criteo.demo.engine.dao.ProductViewRepository;
import com.criteo.demo.engine.model.ProductView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProductViewRepository productViewRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "${kafka.topic.view-product}")
  public void receive(String payload) throws IOException {
    LOGGER.info("received payload='{}'", payload);
    latch.countDown();
    KafkaProductViewMessage kafkaProductViewMessage = this.objectMapper.readValue(payload, KafkaProductViewMessage.class);
    productViewRepository.save(new ProductView(kafkaProductViewMessage.getUserId(), kafkaProductViewMessage.getProductId(), kafkaProductViewMessage.getTimestamp()));
  }
}