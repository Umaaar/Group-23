package org.example.controller;

import org.example.model.CustomUserDetail;
import org.example.repository.CatagoryRepository;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    CategoryService catagoryService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/index";
    }

    @GetMapping("/customer-dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetail authentication, HttpServletResponse response,
            Model model) {
        model.addAttribute("firstname", authentication.getFirstname());
        System.out.println(authentication.getFirstname());
        model.addAttribute("lastname", authentication.getLastname());
        System.out.println(authentication.getLastname());
        model.addAttribute("email", authentication.getEmail());
        System.out.println(authentication.getEmail());
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/customer-dashboard";
    }

    @GetMapping("/orders")
    public String Orders(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/orders";
    }

    @GetMapping("/contact-us")
    public String ContactPage(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/contact-details";
    }

    @GetMapping("/about-us")
    public String AboutPage(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/about-us";
    }

    @GetMapping("/faq")
    public String Faq(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/faq";
    }

    @GetMapping("/returns")
    public String Returns(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/returns";
    }

}
