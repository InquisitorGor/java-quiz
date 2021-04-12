package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.UserDataRepository;
import ru.ayubdzhanov.javaquiz.domain.UserData;

@Service
public class UserDataService {

    @Autowired
    private UserDataContainer userDataContainer;
    @Autowired
    private UserDataRepository userDataRepository;

    public UserData getUserData(){
        return userDataRepository.getOne(userDataContainer.getId());
    }
}
