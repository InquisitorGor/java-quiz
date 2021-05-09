package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Type;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@Service
public class TheoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private List<Category> categories;

    public List<Category> getCategories(Boolean forceUpdate) {
        categories = categoryRepository.findAll();
        wrapCategories(categories);
        categories.forEach(category -> wrapTheories(category.getTheories()));
        return categories;
    }

    //FIXME Correct attach logic
    // - add matchers for attach
    // - add StringBuilder
    // - add Exception handling
    public void wrapTheories(List<Theory> theories){
        theories.forEach(theory -> {
            if (theory.getAttachments().isEmpty()) {
                theory.setParsedDescription(theory.getDescription());
                return;
            }
            AtomicReference<String> parsedDescription = new AtomicReference<>(theory.getDescription());
            theory.getAttachments().forEach(attachment -> {
                if (attachment.getType() == Type.IMAGE) {
                    Pattern firstImagePattern = Pattern.compile(".*(картинка1).*");
                    Pattern secondImagePattern = Pattern.compile(".*(картинка2).*");
                    Pattern thirdImagePattern = Pattern.compile(".*(картинка3).*");
                    if (firstImagePattern.matcher(attachment.getPath()).matches()){
                        parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 1%", attachment.getSize(), attachment.getCaption()));
                    } else if (secondImagePattern.matcher(attachment.getPath()).matches()) {
                        parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 2%", attachment.getSize(), attachment.getCaption()));
                    } else if (thirdImagePattern.matcher(attachment.getPath()).matches()) {
                        parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 3%", attachment.getSize(), attachment.getCaption()));
                    }
                } else if (attachment.getType() == Type.VIDEO) {
                    parsedDescription.set(HtmlUtils.parseVideoLink(parsedDescription.get(), attachment.getPath(), "%видео%"));
                } else {
                    try {
                        throw new Exception("Unknown attachment type");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            theory.setParsedDescription(parsedDescription.get());
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
