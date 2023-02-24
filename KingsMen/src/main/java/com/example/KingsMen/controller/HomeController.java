package com.example.KingsMen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/CustomerDashboard")
    public String dashboard() {
        return "/frontend-views/customer-dashboard.html";
    }

    @GetMapping("/Contact-us")
    public String ContactPage() {
        return "/frontend-views/contact-details";
    }
}
