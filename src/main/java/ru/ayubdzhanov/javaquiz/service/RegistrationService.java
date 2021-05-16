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

    public void saveUser(String login, String password, String username) throws UserException{
        if (userCredentialRepository.findByLogin(login) != null) throw new UserException("User already exists");
        UserData userData = new UserData();
        userData.setName(username);
        userData.setPrestige(0);
        userData.setAmountOfBattles(0);
        userCredentialRepository.save(new UserCredential(login, passwordEncoder.encode(password), Roles.USER, userData));
    }
}
