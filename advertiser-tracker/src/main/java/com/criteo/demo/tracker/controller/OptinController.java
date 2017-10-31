package com.criteo.demo.tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptinController {

    @RequestMapping("/optin/register")
    public String register(@RequestParam(name="id") int id) {
        return "OK" + id;
    }
}
