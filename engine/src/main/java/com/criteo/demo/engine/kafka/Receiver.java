package com.criteo.demo.engine.kafka;

import com.criteo.demo.common.model.KafkaProductViewMessage;
import com.criteo.demo.common.dao.ProductViewRepository;
import com.criteo.demo.common.model.ProductView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

  private final ObjectMapper objectMapper;

  private final ProductViewRepository productViewRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired
  public Receiver(ObjectMapper objectMapper, ProductViewRepository productViewRepository) {
    this.objectMapper = objectMapper;
    this.productViewRepository = productViewRepository;
  }

  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "${kafka.topic.view-product}")
  public void receive(String payload) throws IOException {
    LOGGER.info("received payload='{}'", payload);
    latch.countDown();
    KafkaProductViewMessage kafkaProductViewMessage = this.objectMapper.readValue(payload, KafkaProductViewMessage.class);
    productViewRepository.save(new ProductView(kafkaProductViewMessage.getUserId(), kafkaProductViewMessage.getProductId(), new Date(kafkaProductViewMessage.getTimestamp())));
  }
}