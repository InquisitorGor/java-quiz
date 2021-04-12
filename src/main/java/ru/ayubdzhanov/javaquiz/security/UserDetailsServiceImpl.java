package ru.ayubdzhanov.javaquiz.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.dao.UserCredentialRepository;
import ru.ayubdzhanov.javaquiz.domain.UserCredential;
import ru.ayubdzhanov.javaquiz.domain.UserData;
import ru.ayubdzhanov.javaquiz.service.UserDataContainer;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;
    private final UserDataContainer userDataContainer;

    public UserDetailsServiceImpl(UserCredentialRepository userCredentialRepository, UserDataContainer userDataContainer) {
        this.userCredentialRepository = userCredentialRepository;
        this.userDataContainer = userDataContainer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential credential = userCredentialRepository.findByLogin(username);
        if (credential == null) throw new UsernameNotFoundException(username + " was not found");
        userDataContainer.setId(credential.getId());
        UserBuilder user = User
            .withUsername(username)
            .password(credential.getPassword())
            .roles(credential.getRole());
        return user.build();
    }
}
