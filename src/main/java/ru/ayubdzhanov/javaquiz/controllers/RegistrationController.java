package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ayubdzhanov.javaquiz.exception.UserException;
import ru.ayubdzhanov.javaquiz.service.RegistrationService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public String showRegistrationPage(@RequestParam String username,
                                       @RequestParam String password,
                                       Model model) {
        try {
            registrationService.saveUser(username, password);
            model.addAttribute("success", Boolean.TRUE);
        } catch (UserException ex) {
            model.addAttribute("success", Boolean.FALSE);
        }
        return "registrationPage";
    }


    @GetMapping("")
    public String showRegistrationPage() {
        return "registrationPage";
    }
}
