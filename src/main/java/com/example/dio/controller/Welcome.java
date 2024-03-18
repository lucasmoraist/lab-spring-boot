package com.example.dio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @GetMapping
    public String HelloWorld(){
        return "Welcome to my API Spring Boot with Bootcamp Santander";
    }

}
