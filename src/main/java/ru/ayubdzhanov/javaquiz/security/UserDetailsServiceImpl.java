package ru.ayubdzhanov.javaquiz.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.dao.CredentialRepository;
import ru.ayubdzhanov.javaquiz.domain.Credential;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Credential credential = credentialRepository
                .findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User " + name + " was not found"));

        return User.withUsername(name)
                .password(credential.getPassword())
                .authorities(credential.getRole())
                .build();
    }
}
