package ru.ayubdzhanov.javaquiz.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegistrationController {
    @PostMapping("/register")
    public String register(@RequestParam(value = "username", required = false) String name, @RequestParam(value = "password", required = false) String password) {
        log.info("Name " + name + " Password " + password);
        return "Data received successfully by server";

    }
}
