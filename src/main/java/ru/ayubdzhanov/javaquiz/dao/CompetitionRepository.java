package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.Competition;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query("SELECT c FROM Competition c " +
        "INNER JOIN c.contestants con " +
        "WHERE c.finishedAt IS NULL " +
        "AND con.userData.id = ?1 ")
    List<Competition> findAllChallenges(Long currentUserId, Pageable pageable);

    @Query("SELECT c FROM Competition c " +
        "INNER JOIN c.contestants con " +
        "WHERE c.finishedAt IS NOT NULL " +
        "AND con.userData.id = ?1 " +
        "ORDER BY c.finishedAt DESC ")
    List<Competition> findAllCompletedCompetitionsByUserId(Long currentUserId, Pageable pageable);

    @Query("SELECT c FROM Competition c " +
        "WHERE c.finishedAt IS NOT NULL " +
        "AND c.category.id = ?1 " +
        "ORDER BY c.finishedAt DESC ")
    List<Competition> findAllCompletedCompetitions(Long categoryId, Pageable pageable);

}
