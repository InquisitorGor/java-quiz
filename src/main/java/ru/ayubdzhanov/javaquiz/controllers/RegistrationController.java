package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ayubdzhanov.javaquiz.exception.UserException;
import ru.ayubdzhanov.javaquiz.service.UserCredentialService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserCredentialService userCredentialService;

    public RegistrationController(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    @PostMapping("/register")
    public String showRegistrationPage(@RequestParam String username,
                                       @RequestParam String password,
                                       Model model) {
        try {
            userCredentialService.saveUser(username, password);
            model.addAttribute("success", Boolean.TRUE);
        } catch (UserException ex) {
            model.addAttribute("success", Boolean.FALSE);
        }
        return "registrationPage";
    }
}
