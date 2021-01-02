package ru.ayubdzhanov.javaquiz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JavaQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaQuizApplication.class, args);
        log.debug("Debug");
    }


}
