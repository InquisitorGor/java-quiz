package ru.ayubdzhanov.javaquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("dev")
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Test
    void getTheories() {
        adminService.getTheories("такое").forEach(theory -> {
            System.out.println(theory.getTitle());
        });
    }
    @Test
    void getTheories1() {
        adminService.getTheories("JVM").forEach(theory -> {
            System.out.println(theory.getTitle());
        });
    }
    @Test
    void getTheories2() {
        adminService.getTheories("данн").forEach(theory -> {
            System.out.println(theory.getTitle());
        });
    }
}