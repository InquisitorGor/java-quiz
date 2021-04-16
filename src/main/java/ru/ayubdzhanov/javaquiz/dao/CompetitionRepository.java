package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.Competition;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query("SELECT c FROM Competition c " +
        "INNER JOIN c.contestants con " +
        "WHERE c.finishedAt IS NULL " +
        "AND c.category.id = ?1 " +
        "AND NOT con.userData.id = ?2 ")
    List<Competition> findAllStartedCompetitions(Long categoryId, Long currentUserId);

}
