package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.service.TheoryService;

import java.util.List;

@Controller
@RequestMapping("/theory")
public class TheoryController {

    @Autowired
    private TheoryService theoryService;

    @RequestMapping("/list/{category}")
    public String showTheoryPage(Model model,
                                 @PathVariable String category,
                                 @RequestParam(required = false) String keyword) {
        model.addAttribute("theories", theoryService.getTheories(category, keyword, 0));
        model.addAttribute("selectedKeyword", keyword);
        model.addAttribute("category", theoryService.getCategory(category));



        return "theoryPage";
    }

    @RequestMapping("/detail/{theoryId}")
    public String showTheoryDetailPage(Model model,
                                 @PathVariable String theoryId) {
        model.addAttribute("theory", theoryService.getTheory(Long.parseLong(theoryId)));
        return "theoryDetailPage";
    }

    @RequestMapping(value = "/list/{category}", params = "olderThan")
    @ResponseBody
    public List<Theory> getTheories(@PathVariable String category,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam String olderThan) {
        return theoryService.getTheories(category, keyword, Integer.parseInt(olderThan));
    }

    @GetMapping("/categories")
    public String showTheoryListPage(Model model) {
        model.addAttribute("categories", theoryService.getCategories());
        return "theoryListPage";
    }
}
