package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCategoryIdAndIsApproved(Long categoryId, Boolean isApproved, Pageable pageable);

    List<Task> findAllByIsApproved(Boolean isApproved, Pageable pageable);

    @Query("SELECT t FROM Task t " +
        "WHERE t.question LIKE %?1% " +
        "AND t.category.category = ?2 " +
        "AND t.isApproved = ?3")
    List<Task> findAllByTitleAndCategoryAndIsApproved(String regex, String category, Boolean isApproved, Pageable pageable);

    @Query("SELECT t FROM Task t " +
        "WHERE t.question LIKE %?1% " +
        "AND t.isApproved = ?2")
    List<Task> findAllByQuestionAndIsApproved(String regex, Boolean isApproved, Pageable pageable);

    List<Task> findAllByCategoryCategoryAndIsApproved(String category, Boolean isApproved, Pageable pageable);
}
