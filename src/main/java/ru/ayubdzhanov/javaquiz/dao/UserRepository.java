package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ayubdzhanov.javaquiz.domain.UserCredential;

@Repository
public interface UserRepository extends JpaRepository<UserCredential, Long> {
    UserCredential findByName(String name);
}
