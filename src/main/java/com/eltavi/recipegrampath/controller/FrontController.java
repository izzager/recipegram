package com.eltavi.recipegrampath.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FrontController {

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/test")
    public String test() {
        return "THIS IS STRING";
    }
}
