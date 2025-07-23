package com.example.insta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marks this as a REST controller (no view)
public class HelloController {

    @GetMapping("/") // Maps GET / to this method
    public String hello() {
        return "Hello World";
    }
}
