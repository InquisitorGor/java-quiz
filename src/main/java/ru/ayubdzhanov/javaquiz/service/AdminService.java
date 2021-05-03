package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private TheoryRepository theoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public List<Theory> getTheories(String category, String keyword){ // better to use Criteria API
        if (category == null && keyword == null) return theoryRepository.findAll(PageRequest.of(0, 20)).getContent();
        else if (category != null && keyword == null) return theoryRepository.findAllByCategoryCategory(category);
        else if (category == null) return theoryRepository.findAllByTitle(keyword);
        return theoryRepository.findAllByTitleAndCategory(keyword, category);
    }
    public List<Theory> getTheories(String regex){
        return theoryRepository.findAllByTitle(regex);
    }
}
