package ru.ayubdzhanov.javaquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ayubdzhanov.javaquiz.domain.Credential;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByName(String name);
}
