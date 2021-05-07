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
import ru.ayubdzhanov.javaquiz.exception.HtmlValidationException;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HtmlValidatorAdapter {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${html-validator.url}")
    private String url;

    public void validateHtml(String content){
        if (content.isEmpty()) {
            throw new HtmlValidationException("validation failed", "empty content");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> request = new HttpEntity<>(HtmlUtils.wrapHtmlAttributes(content), headers);
        ValidatorResponse result = restTemplate.postForObject(url, request, ValidatorResponse.class);
        if (!result.getMessages().isEmpty()) {
            String errors = result.getMessages().stream()
                .filter(msg -> msg.get("type").equals("error"))
                .filter(msg -> !msg.get("message").startsWith("Malformed byte sequence"))
                .map(msg -> msg.get("message"))
                .collect(Collectors.joining("\r\n"));
            if (!errors.isEmpty()) throw new HtmlValidationException("validation failed", errors);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ValidatorResponse {
        ArrayList<Map<String, String>> messages;
    }
}
