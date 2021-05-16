package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;

import java.util.Optional;

@Repository
public interface CompetitionInfoRepository extends JpaRepository<CompetitionInfo, Long> {
    Optional<CompetitionInfo> findByCategoryId(Long categoryId);
}
