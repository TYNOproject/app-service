package com.appservice.all.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class MainController {

    @GetMapping
    public String helloWorld(){
        return "Hello!";
    }
}
