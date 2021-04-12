package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ayubdzhanov.javaquiz.service.UserDataService;

@Controller
public class ProfileController {

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/user_page")
    public String showUserPage(Model model) {
        model.addAttribute("profile", userDataService.getUserData());
        return "userPage";
    }
}
