package com.servlet.contextpathissue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testAPI() {
        return "Called Controller::::";
    }
}
