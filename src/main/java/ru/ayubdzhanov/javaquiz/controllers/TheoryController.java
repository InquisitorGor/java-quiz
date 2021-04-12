package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.service.TheoryService;

import java.util.List;

@Controller
public class TheoryController {

    @Autowired
    private TheoryService theoryService;

    @GetMapping("/theory")
    public String showTheoryPage(Model model) {
        List<Theory> all = theoryService.getTheories();
        model.addAttribute("theories", all);
        return "theoryPage";
    }
}
