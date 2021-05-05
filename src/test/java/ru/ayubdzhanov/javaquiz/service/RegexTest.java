package ru.ayubdzhanov.javaquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;


public class RegexTest {

    @Test
    void getTheories() {
        List<String> files = Arrays.asList("rere.txt", "rera.jpg", "hola.png", null);
        Pattern pattern = Pattern.compile(".*(\\.jpg|\\.png)");
        List<String> collect = files.stream()
            .filter(Objects::nonNull)
            .filter(file -> pattern.matcher(file)
                .matches()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertFalse(collect.stream().anyMatch(file -> file.equals("rere.txt")));
    }
}
