package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
