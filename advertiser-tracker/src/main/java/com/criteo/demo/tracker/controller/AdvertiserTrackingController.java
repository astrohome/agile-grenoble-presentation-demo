package com.criteo.demo.tracker.controller;

import com.criteo.demo.tracker.kafka.Sender;
import com.criteo.demo.tracker.model.ViewProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertiserTrackingController {
    private Logger log = LoggerFactory.getLogger(ClickController.class);

    private final Sender sender;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdvertiserTrackingController(Sender sender, ObjectMapper objectMapper) {
        this.sender = sender;
        this.objectMapper = objectMapper;
    }

    @RequestMapping("/api/advertiser-tracker/view")
    public void click(@RequestParam(name = "userid") int userId, @RequestParam(name = "productid") int productId)
            throws JsonProcessingException {
        log.debug("user_id: " + userId);
        ViewProduct viewProduct = new ViewProduct(userId, productId, System.currentTimeMillis());
        this.sender.send("view_product", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(viewProduct));
    }
}
