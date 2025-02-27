package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserEntityByUsername(username)
                .map((userEntity) -> new User(userEntity.getUsername(), userEntity.getPasswordHash(), Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username:  " + username));
    }
}
