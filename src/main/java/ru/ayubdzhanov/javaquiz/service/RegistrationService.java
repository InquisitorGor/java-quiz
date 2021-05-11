package ru.ayubdzhanov.javaquiz.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.UserCredentialRepository;
import ru.ayubdzhanov.javaquiz.domain.UserCredential;
import ru.ayubdzhanov.javaquiz.domain.UserCredential.Roles;
import ru.ayubdzhanov.javaquiz.domain.UserData;
import ru.ayubdzhanov.javaquiz.exception.UserException;

@Service
public class RegistrationService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(String name, String password) throws UserException{
        if (userCredentialRepository.findByLogin(name) != null) throw new UserException("User already exists");
        UserData userData = new UserData();
        userCredentialRepository.save(new UserCredential(name, passwordEncoder.encode(password), Roles.USER, userData));
    }
}
