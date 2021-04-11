package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ayubdzhanov.javaquiz.service.TheoryService;

@Controller
public class TheoryController {

    @Autowired
    private TheoryService theoryService;

    @GetMapping("/theory")
    public String showTheoryPage(Model model) {
        model.addAttribute("content", theoryService.findAll());
        return "theoryPage";
    }
}
