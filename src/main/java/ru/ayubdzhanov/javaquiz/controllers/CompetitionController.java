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


@Controller
@RequestMapping("/competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @RequestMapping("/battle/{category_id}")
    public String showBattlePage(Model model, @PathVariable("category_id") Long categoryId, @RequestParam(required = false) Long existedCompetitionId) {
        Competition competition = competitionService.getCompetition(categoryId, existedCompetitionId);
        model.addAttribute("competition", competition);
        model.addAttribute("opponent", competitionService.getOpponent(competition));
        model.addAttribute("currentContestantId", competitionService.getCurrentContestantId());
        return "battlePage";
    }

    @GetMapping("/list")
    public String showCompetitionListPage(Model model) {
        model.addAttribute("competitionList", competitionService.getCompetitionsList());
        model.addAttribute("challenges", competitionService.getChallenges());
        model.addAttribute("waitingCompetitions", competitionService.getWaitingCompetitions());
        model.addAttribute("oldBattles", competitionService.getContestantBattleHistory());
        model.addAttribute("currentContestantId", competitionService.getCurrentContestantId());
        return "competitionListPage";
    }

    @GetMapping("/result/{competitionId}")
    public String showCompetitionResultPage(Model model, @PathVariable Long competitionId){
        Competition competition = competitionService.getCompetition(null, competitionId);
        model.addAttribute("correctResults", competitionService.getCompetitionsCorrectResults(competition));
        model.addAttribute("currentContestantResults", competitionService.getContestantResults(competition, Boolean.TRUE));
        model.addAttribute("opponentResults", competitionService.getContestantResults(competition, Boolean.FALSE));
        model.addAttribute("currentContestant", competitionService.getCurrentContestant(competition));
        model.addAttribute("opponent", competitionService.getOpponent(competition));
        model.addAttribute("imageLink", competition.getViewUtil().getImageLink());
        return "competitionResultsPage";
    }

    @GetMapping("/{category_id}")
    public String showCompetitionPage(Model model, @PathVariable("category_id") String categoryId) {
        model.addAttribute("competitionInfo", competitionService.getCompetitionInfo(categoryId));
        model.addAttribute("pastCompetitions", competitionService.getPastCompetitions(Long.parseLong(categoryId)));
        return "competitionPage";
    }

    @PostMapping("/battle/finish")
    public String finishBattle(@RequestParam MultiValueMap<String,String> allParams){
        competitionService.finishCompetition(allParams);
        return "redirect:/competition/list";
    }
}
