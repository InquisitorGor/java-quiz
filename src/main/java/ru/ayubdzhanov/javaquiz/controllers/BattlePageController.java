package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/battle")
public class BattlePageController {

    @PostMapping("/startBattle")
    public String showBattlePage(@RequestParam String value) {
        System.out.println("We got this value " + value);
        return "battlePage";
    }
}
