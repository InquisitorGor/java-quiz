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

    public List<Theory> findAll(){
        return theoryRepository.findAll();
    }
}
