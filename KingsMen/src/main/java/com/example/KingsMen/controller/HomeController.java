package com.example.KingsMen.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }
}
