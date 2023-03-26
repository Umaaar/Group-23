package org.example.controller;

import org.example.model.CustomUserDetail;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.CategoryService;
import org.example.repository.ProductRepository;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryService catagoryService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("categories", catagoryService.getAllCategory());
        return "/frontend-views/register";
    }

    @GetMapping(path = "/foo")
    public void foo(HttpSession session) {
        String sessionId = session.getId();
    }

    @GetMapping("/loginSuccess")
    public void getLoginInfo(@AuthenticationPrincipal CustomUserDetail authentication, HttpServletResponse response) throws IOException {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin");

        } else {
            response.sendRedirect("/customer-dashboard");
        }
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException {
        try {
            String password = user.getPassword();
            System.out.println(password);
            System.out.println(user.getFirstname());
            System.out.println(user.getLastname());
            System.out.println(user.getPassword());
            System.out.println(user.getEmail());

            System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setPassword(bCryptPasswordEncoder.encode(password));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);
            request.login(user.getEmail(), password);
            redirectAttributes.addFlashAttribute("successMessage", "You have successfully been registered as a User");
            return "redirect:/";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "This email already exists in the Database");
            return "redirect:/register";
        }



    }

}
