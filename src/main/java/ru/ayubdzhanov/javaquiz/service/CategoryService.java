package ru.ayubdzhanov.javaquiz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void addCategoryByName(String categoryName){
        Category category = new Category();
        category.setCategory(categoryName);
        categoryRepository.save(category);
    }

    public void deleteCategoryById(String categoryId){
        try {
            categoryRepository.deleteById(Long.parseLong(categoryId));
        } catch (Exception exception) {
            log.error("There are key restrictions");
        }
    }

    public Category getCategoryById(String categoryId) throws Exception {
        return categoryRepository.findById(Long.parseLong(categoryId)).orElseThrow(() -> new Exception("no such category"));
    }
}
