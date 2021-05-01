package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping
    public String getAdminPanel(){
        return "adminPage";
    }
}
