package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import java.util.List;

@Service
public class TheoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private List<Category> categories;

    public List<Category> getCategories(Boolean forceUpdate) {
        if (categories == null || forceUpdate == Boolean.TRUE) {
            categories = categoryRepository.findAll();
            wrapCategories(categories);
        }
        return categories;
    }

    public List<Category> getCategories() {
        return getCategories(Boolean.FALSE);
    }

    public void wrapCategories(List<Category> categories){
        categories.forEach(category -> category.setMenu("menu" + category.getId()));
    }

}
