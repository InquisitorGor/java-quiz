package ru.ayubdzhanov.javaquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("dev")
class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    TheoryRepository theoryRepository;
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void testTheory(){
        Theory theory = new Theory();
        theory.setTitle("[eq");
        theory.setDescription("32");
        theoryRepository.save(theory);
    }

    @Test
    public void testTask(){
        Task task = new Task();
        task.setQuestion("hi");
        taskRepository.save(task);
    }



}