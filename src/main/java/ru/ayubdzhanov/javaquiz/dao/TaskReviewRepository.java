package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.TaskReview;

@Repository
public interface TaskReviewRepository extends JpaRepository<TaskReview, Long> {
}
