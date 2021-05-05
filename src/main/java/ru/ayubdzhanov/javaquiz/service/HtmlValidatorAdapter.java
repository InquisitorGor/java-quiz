package ru.ayubdzhanov.javaquiz.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Map;

@Component
public class HtmlValidatorAdapter {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${html-validator.url}")
    private String url;

    public void checkHtml(String content){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> request = new HttpEntity<>(HtmlUtils.processHtml(content), headers);
        ValidatorResponse result = restTemplate.postForObject(url, request, ValidatorResponse.class);
        result.getMessages().forEach(message -> message.entrySet().forEach(System.out::println));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ValidatorResponse {
        ArrayList<Map<String, String>> messages;

    }
}
