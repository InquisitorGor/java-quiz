package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormsController {
    @GetMapping
    public String showMainForm() {
        return "mainPage";
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registrationForm";
    }
}
