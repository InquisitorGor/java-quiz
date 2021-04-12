package ru.ayubdzhanov.javaquiz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

class JavaQuizApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime time = LocalDateTime.now().minusHours(1);
        System.out.println(time);
        System.out.println(time.toLocalDate());
        System.out.println(Period.between(time.toLocalDate(),LocalDate.now()).getDays());
    }


}
