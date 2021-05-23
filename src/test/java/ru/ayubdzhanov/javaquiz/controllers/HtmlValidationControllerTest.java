package ru.ayubdzhanov.javaquiz.controllers;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.service.HtmlValidatorComponent;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HtmlValidationControllerTest {

    public static final String SUCCESSFUL_VALIDATION = "ok";
    private static final String ATTACHMENT_EXCEPTION = "Problem with attachment. There are two cases that trigger this problem:\r\n " +
        "attach is present, but keyword is absent\r\n attach is absent, but keyword is present";
    private static final String EMBEDDED_KEYWORD_EXCEPTION = "Keyword was embedded to html code";
    public static final String KEYWORD_REMOVAL_EXCEPTION = "You forgot to insert a keyword";
    public static final String FIRST_PICTURE_IS_PRESENT_EXCEPTION = "picture 1 exists. delete before updating";

    @Autowired
    private MockMvc mvc;

    @Test
    public void validateTheoryContent_HtmlIsValid_ValidationPassed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .content(
                "<p>«Коллекция» - это структура данных, набор каких-либо объектов. " +
                    "Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p>")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(SUCCESSFUL_VALIDATION));
    }
    @Test
    public void validateTheoryContent_HtmlIsValidAndContainsPictureAttachment_ValidationPassed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .param("firstImageExist", "true")
            .content(
                "<p>«Коллекция» - это структура данных, набор каких-либо объектов. " +
                    "Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p>%картинка 1%")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(SUCCESSFUL_VALIDATION));
    }
    @Test
    public void validateTheoryContent_HtmlIsValidAndContainsVideoLinkAttachment_ValidationPassed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .param("videoLinkAttachExist", "true")
            .content(
                "<p>«Коллекция» - это структура данных, набор каких-либо объектов. " +
                    "Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p>%видео%")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(SUCCESSFUL_VALIDATION));
    }

    @Test
    public void validateTheoryContent_HtmlIsInvalidAndHasAStrayTag_ValidationFailed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .content(
                "<p>«Коллекция» - это структура данных, набор каких-либо объектов. " +
                    "Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p2>")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(CoreMatchers.not(SUCCESSFUL_VALIDATION)));
    }

    @Test
    public void validateTheoryContent_KeywordIsAbsent_ValidationFailed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .param("firstImageExist", "true")
            .content(
                "<p>«Коллекция» - это структура данных, набор каких-либо объектов. " +
                    "Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p>")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(ATTACHMENT_EXCEPTION));
    }

    @Test
    public void validateTheoryContent_KeywordIsInsertedToHtmlCodeIncorrectly_ValidationFailed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .param("firstImageExist", "true")
            .content(
                "<p>«Коллекция»  %картинка 1% - это структура данных, набор каких-либо объектов. " +
                    "Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p>")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(EMBEDDED_KEYWORD_EXCEPTION));
    }

    @Test
    public void validateTheoryContent_KeywordIsRemovedButAttachIsPresent_ValidationFailed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .param("theoryId", "1")
            .content(
                "<p>Объектно-ориентированное программирование (ООП) — " +
                    "методология программирования</p>")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(KEYWORD_REMOVAL_EXCEPTION));
    }

    @Test
    public void validateTheoryContent_NewAndOldAttachArePresentInTheSameTime_ValidationFailed() throws Exception {
        mvc.perform(post("/validation/validateTheoryContent")
            .param("theoryId", "1")
            .param("firstImageExist", "true")
            .content(
                "<p>Объектно-ориентированное программирование (ООП) — " +
                    "методология программирования</p> %картинка 1% %картинка 2%")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(FIRST_PICTURE_IS_PRESENT_EXCEPTION));
    }

}