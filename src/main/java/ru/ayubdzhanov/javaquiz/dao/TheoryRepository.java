package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import java.util.List;

@Repository
public interface TheoryRepository extends JpaRepository<Theory, Long> {

    @Query("SELECT t FROM Theory t " +
        "WHERE t.title LIKE %?1% " +
        "AND t.category.category = ?2")
    List<Theory> findAllByTitleAndCategory(String regex, String category, Pageable pageable);

    @Query("SELECT t FROM Theory t " +
           "WHERE t.title LIKE %?1%")
    List<Theory> findAllByTitle(String regex, Pageable pageable);

    List<Theory> findAllByCategoryCategory(String category, Pageable pageable);
}
