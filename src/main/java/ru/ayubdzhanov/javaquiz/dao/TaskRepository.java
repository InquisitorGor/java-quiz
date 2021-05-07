package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT t FROM Task t " +
        "WHERE t.question LIKE %?1% " +
        "AND t.category.category = ?2")
    List<Task> findAllByTitleAndCategory(String regex, String category);

    @Query("SELECT t FROM Task t " +
        "WHERE t.question LIKE %?1%")
    List<Task> findAllByQuestion(String regex);

    List<Task> findAllByCategoryCategory(String category);
}
