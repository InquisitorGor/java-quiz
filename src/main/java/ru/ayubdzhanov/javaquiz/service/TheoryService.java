package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import java.util.List;

@Service
public class TheoryService {

    @Autowired
    private TheoryRepository theoryRepository;
    private List<Theory> theories;

    public List<Theory> getTheories(Boolean forceUpdate) {
        if (theories == null || forceUpdate == Boolean.TRUE) {
            theories = theoryRepository.findAll();
            wrapTheories(theories);
        }
        return theories;
    }

    public List<Theory> getTheories() {
        return getTheories(Boolean.FALSE);
    }

    public void wrapTheories(List<Theory> theories){
        theories.forEach(theory -> theory.setMenu("menu" + theory.getId()));
    }

    public class Content {

    }

}
