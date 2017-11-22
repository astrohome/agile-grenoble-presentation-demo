package com.criteo.demo.tracker.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public Sender(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, String payload) {
    LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
    kafkaTemplate.send(topic, payload);
  }
}