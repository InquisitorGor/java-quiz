package ru.ayubdzhanov.javaquiz.service;

import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.UserRepository;
import ru.ayubdzhanov.javaquiz.domain.UserCredential;
import ru.ayubdzhanov.javaquiz.exception.UserException;

@Service
public class UserCredentialService {
    private final UserRepository userRepository;

    public UserCredentialService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String name, String password) throws UserException{
        if (userRepository.findByName(name) != null) throw new UserException("User already exists");
        userRepository.save(new UserCredential(name, password, "USER"));
    }
}
