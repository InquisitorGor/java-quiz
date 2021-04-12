package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompetitionController {

    @GetMapping("/battle")
    public String showBattlePage() {
        return "battlePage";
    }

    @GetMapping("/competition/list")
    public String showCompetitionListPage(Model model) {
        return "competitionListPage";
    }

    @GetMapping("/competition")
    public String showCompetitionPage(Model model) {
        return "competitionPage";
    }
}
