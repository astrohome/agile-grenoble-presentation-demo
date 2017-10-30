package com.criteo.demo.tracker.controller;

import com.criteo.demo.tracker.kafka.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClickController {

    private Logger log = LoggerFactory.getLogger(ClickController.class);

    private final Sender sender;

    @Autowired
    public ClickController(Sender sender) {
        this.sender = sender;
    }

    @RequestMapping("/click")
    public void click(@RequestParam(name="userid") int userId) {
        log.debug("user_id: " + userId);
        this.sender.send("click", "{user_id:" + userId + "}");
    }
}
