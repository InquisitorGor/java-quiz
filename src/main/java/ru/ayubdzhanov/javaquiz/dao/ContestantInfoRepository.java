package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.ContestantInfo;

import java.util.List;

@Repository
public interface ContestantInfoRepository extends JpaRepository<ContestantInfo, Long> {
    @Query("SELECT cI FROM ContestantInfo cI " +
        "INNER JOIN cI.competition com " +
        "WHERE NOT cI.userData.id = ?1 " +
        "AND com.category.id = ?2 ")
    List<ContestantInfo> findAllAccessibleContestants(Long userId, Long categoryId);

}
