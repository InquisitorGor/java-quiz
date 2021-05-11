package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.service.TheoryService;

import java.util.List;

@Controller
public class TheoryController {

    @Autowired
    private TheoryService theoryService;

    @GetMapping("/theory")
    public String showTheoryPage(Model model) {
        model.addAttribute("categories", theoryService.getCategories());
        return "theoryPage";
    }
}
