package org.example.controller;

import org.example.model.*;
import org.example.service.CategoryService;
import org.example.service.ContactService;
import org.example.service.CustomUserDetailService;
import org.example.service.OrderItemService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    ContactService contactService;

    @Autowired
    CategoryService catagoryService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CustomUserDetailService auth;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/index";
    }

    @GetMapping("/customer-dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetail authentication, HttpServletResponse response,
            Model model) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            return "redirect:/admin";

        } else {

            model.addAttribute("firstname", authentication.getFirstname());
            System.out.println(authentication.getFirstname());
            model.addAttribute("lastname", authentication.getLastname());
            System.out.println(authentication.getLastname());
            model.addAttribute("email", authentication.getEmail());
            System.out.println(authentication.getEmail());
            model.addAttribute("categories", catagoryService.getAllCategory());

            return "/frontend-views/customer-dashboard";
        }

    }

    @GetMapping("/customer-dashboard/orders")
    public String Orders(@AuthenticationPrincipal OrderDetails order, @AuthenticationPrincipal CustomUserDetail authentication, 
            HttpServletResponse response, Model model) {
        model.addAttribute("orders", orderService.findByKeyword(authentication.getFirstname()));
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/orders";
    }

    @GetMapping("/customer-dashboard/orders/details/{id}")
    public String getOrderItems(@PathVariable Integer id, Model model, @AuthenticationPrincipal CustomUserDetail authentication) {
        OrderDetails order = orderService.getOrderByorder_id(id).get();
        List<OrderItem> orderItems = orderItemService.getAllOrderItemsByID(order.getId());
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("categories", catagoryService.getAllCategory());

        return "/frontend-views/order-details";
    }

    @GetMapping("/contact-us")
    public String ContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/contact-details";
    }

    @PostMapping("/contact-us")
    public String postContact(@ModelAttribute("contact") Contact contact, RedirectAttributes redirectAttributes) {
        try {
            contactService.save(contact);
            redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please try again, message was not sent.");
        }
         return "redirect:/contact-us";
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
