package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ayubdzhanov.javaquiz.domain.Competition;
import ru.ayubdzhanov.javaquiz.service.CompetitionService;
import ru.ayubdzhanov.javaquiz.service.UserDataContainer;


@Controller
@RequestMapping("/competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private UserDataContainer userDataContainer;

    @GetMapping("/battle/{category_id}")
    public String showBattlePage(Model model, @PathVariable("category_id") Long categoryId) {
        Competition competition = competitionService.getCompetition(categoryId);
        model.addAttribute("competition", competition);
        model.addAttribute("opponent", competitionService.getOpponent(competition));
        model.addAttribute("currentContestantId", userDataContainer.getId());
        return "battlePage";
    }

    @GetMapping("/list")
    public String showCompetitionListPage(Model model) {
        model.addAttribute("competitionList", competitionService.getCompetitionsList());
        return "competitionListPage";
    }

    @GetMapping("/{category_id}")
    public String showCompetitionPage(Model model, @PathVariable("category_id") Long categoryId) {
        model.addAttribute("competitionInfo", competitionService.getCompetitionInfo(categoryId));
        return "competitionPage";
    }

    @PostMapping("/battle/finish")
    public String showBattleResultPage(@RequestParam MultiValueMap<String,String> allParams){
        competitionService.finishCompetition(allParams);
        return "redirect:/";
    }
}
