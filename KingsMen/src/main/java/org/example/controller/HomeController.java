package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "/frontend-views/index";
    }

    @GetMapping("/customer-dashboard")
    public String dashboard() {
        return "/frontend-views/customer-dashboard";
    }

    @GetMapping("/contact-us")
    public String ContactPage() {
        return "/frontend-views/contact-details";
    }

    @GetMapping("/about-us")
    public String AboutPage() {
        return "/frontend-views/about-us";
    }

  

}
