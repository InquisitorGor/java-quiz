package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ayubdzhanov.javaquiz.exception.HtmlValidationException;
import ru.ayubdzhanov.javaquiz.service.HtmlValidatorComponent;


@RestController
@RequestMapping("/validation")
public class HtmlValidationController {

    @Autowired
    private HtmlValidatorComponent htmlValidatorComponent;

    @RequestMapping("/validate")
    public ResponseEntity<String> validateHtml(@RequestBody String content,
                                               @RequestParam(required = false, defaultValue = "false") Boolean firstImageExist,
                                               @RequestParam(required = false, defaultValue = "false") Boolean secondImageExist,
                                               @RequestParam(required = false, defaultValue = "false") Boolean thirdImageExist,
                                               @RequestParam(required = false, defaultValue = "false") Boolean videoLinkAttachExist,
                                               @RequestParam(required = false, defaultValue = "0") String theoryId) {
        try {
            htmlValidatorComponent.checkHtml(content, firstImageExist, secondImageExist, thirdImageExist, videoLinkAttachExist, theoryId);
            return ResponseEntity.ok("ok");
        } catch (HtmlValidationException ex) {
            return ResponseEntity.ok(ex.getErrors());
        }
    }
}
