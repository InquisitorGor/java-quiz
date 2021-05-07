package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import java.util.List;

@Service
public class TheoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private List<Category> categories;

    public List<Category> getCategories(Boolean forceUpdate) {
        if (categories == null || forceUpdate == Boolean.TRUE) {
            categories = categoryRepository.findAll();
            categories.forEach(category -> wrapTheories(category.getTheories()));
            wrapCategories(categories);
        }
        return categories;
    }

    public void wrapTheories(List<Theory> theories){
        theories.forEach(theory -> {
            theory.getAttachments().forEach(attachment -> {
                String parsedDescription = HtmlUtils.parseLinks(theory.getDescription(), attachment.getPath(), "%картинка 2%");
                parsedDescription = HtmlUtils.parseLinks(parsedDescription, attachment.getPath(), "%картинка 1%");
                parsedDescription = HtmlUtils.parseLinks(parsedDescription, attachment.getPath(), "%картинка 3%");
                theory.setParsedDescription(parsedDescription);
            });
        });
    }

    public List<Category> getCategories() {
        return getCategories(Boolean.FALSE);
    }

    private void wrapCategories(List<Category> categories){
        categories.forEach(category -> category.setViewUtil(ViewUtils
            .builder()
            .menu("menu" + category.getId())
            .build()));
    }

}
