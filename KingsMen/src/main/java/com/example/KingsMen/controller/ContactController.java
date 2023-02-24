package com.example.KingsMen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/")
    public String ContactPage() {
        return "/frontend-views/contact-details";
    }
}
