package org.example.controller;

import org.example.model.CustomUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "/frontend-views/index";
    }

    @GetMapping("/customer-dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetail authentication, HttpServletResponse response , Model model) {
        model.addAttribute("firstname",authentication.getFirstname());
        System.out.println(authentication.getFirstname());
        model.addAttribute("email",authentication.getEmail());
        System.out.println(authentication.getEmail());
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
