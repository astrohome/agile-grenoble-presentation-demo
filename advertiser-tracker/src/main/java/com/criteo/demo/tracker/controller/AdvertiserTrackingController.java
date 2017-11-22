package com.criteo.demo.tracker.controller;

import com.criteo.demo.common.model.KafkaProductViewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class AdvertiserTrackingController {
    private Logger log = LoggerFactory.getLogger(AdvertiserTrackingController.class);

    @Value("${kafka.topic.view-product}")
    private String viewProductTopic;

    private final KafkaTemplate<String, KafkaProductViewMessage> kafkaTemplate;

    @Autowired
    public AdvertiserTrackingController(KafkaTemplate<String, KafkaProductViewMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @RequestMapping("/api/advertiser-tracker/view")
    public ResponseEntity<String> click(@RequestParam(name = "userid") int userId, @RequestParam(name = "productid") int productId)
            throws JsonProcessingException {
        log.debug("user_id: " + userId);
        KafkaProductViewMessage viewProduct = new KafkaProductViewMessage(userId, productId, System.currentTimeMillis());
        try {
            kafkaTemplate.send(viewProductTopic, viewProduct).get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
