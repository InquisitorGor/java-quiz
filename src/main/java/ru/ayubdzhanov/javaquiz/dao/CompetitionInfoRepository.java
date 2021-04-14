package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;

@Repository
public interface CompetitionInfoRepository extends JpaRepository<CompetitionInfo, Long> {
    CompetitionInfo findByCategoryId(Long categoryId);
}
