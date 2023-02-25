package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "/frontend-views/index";
    }

    @GetMapping("/CustomerDashboard")
    public String dashboard() {
        return "/frontend-views/customer-dashboard";
    }

    @GetMapping("/Contact-us")
    public String ContactPage() {
        return "/frontend-views/contact-details";
    }

    @GetMapping("/About-us")
    public String AboutPage() {
        return "/frontend-views/about-us";
    }

    @GetMapping("/Login")
    public String LoginPage() {
        return "/frontend-views/login";
    }

    @GetMapping("/Register")
    public String RegisterPage() {
        return "/frontend-views/register";
    }


}
