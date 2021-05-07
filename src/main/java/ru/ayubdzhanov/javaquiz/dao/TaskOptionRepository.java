package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.TaskOption;

@Repository
public interface TaskOptionRepository extends JpaRepository<TaskOption, Long> {
}
