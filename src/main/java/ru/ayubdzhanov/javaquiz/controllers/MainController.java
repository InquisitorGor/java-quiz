package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showMainPage(Model model) {
        Object status = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (status instanceof String) {
            String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.equals("anonymousUser")) model.addAttribute("isAuthenticated", false);
        } else {
            model.addAttribute("isAuthenticated", true);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", " " + userDetails.getUsername());
        }
        return "mainPage";
    }

}
