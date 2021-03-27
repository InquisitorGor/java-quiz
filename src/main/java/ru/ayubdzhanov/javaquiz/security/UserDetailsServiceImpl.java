package ru.ayubdzhanov.javaquiz.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.dao.UserRepository;
import ru.ayubdzhanov.javaquiz.domain.UserCredential;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential credential = userRepository.findByName(username);
        if (credential == null) throw new UsernameNotFoundException(username + " was not found");
        UserBuilder user = User
            .withUsername(username)
            .password(credential.getPassword())
            .roles(credential.getRole());
        return user.build();
    }
}
