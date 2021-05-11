package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories(Boolean noWrap) {
        List<Category> categories = categoryRepository.findAll();
        if (!noWrap) {
            wrapCategories(categories);
        }
        return categories;
    }

    private void wrapCategories(List<Category> categories){
        categories.forEach(category -> category.setViewUtil(ViewUtils
            .builder()
            .menu("menu" + category.getId())
            .build()));
    }
}
