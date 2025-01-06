package com.soursoft.budgetit.services;

import com.soursoft.budgetit.dto.PostRegisterRequestDTO;
import com.soursoft.budgetit.dto.PostRegisterResponseDTO;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.soursoft.budgetit.entities.UserEntity.UserEntityBuilder;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PostRegisterResponseDTO registerUser(PostRegisterRequestDTO postRegisterRequestDTO) {
        UserEntity newUser = null;

        Optional<UserEntity> foundUserOptional = userRepository.findUserEntityByUsername(postRegisterRequestDTO.getUsername());

        if(foundUserOptional.isEmpty()) {
            LocalDateTime currentDate = LocalDateTime.now();

            newUser = UserEntityBuilder.getInstance()
                .clear()
                .withActive(true)
                .withUsername(postRegisterRequestDTO.getUsername())
                .withPassword(passwordEncoder.encode(postRegisterRequestDTO.getPassword()))
                .withCreationDate(currentDate)
                .withModificationDate(currentDate)
                .withRemovalDate(null)
                .build();

            newUser = userRepository.save(newUser);
        }

        return PostRegisterResponseDTO.fromEntity(newUser);
    }

}
