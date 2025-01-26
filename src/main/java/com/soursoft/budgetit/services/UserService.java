package com.soursoft.budgetit.services;

import com.soursoft.budgetit.dto.auth.register.PostRegisterRequestDTO;
import com.soursoft.budgetit.dto.auth.register.PostRegisterResponseDTO;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                .withTotalBalance(new BigDecimal(0))
                .withCreationDate(currentDate)
                .withModificationDate(currentDate)
                .withRemovalDate(null)
                .build();

            newUser = userRepository.save(newUser);
        }

        return PostRegisterResponseDTO.fromEntity(newUser);
    }

    //TODO: Create custom exception for handling 'UserEntity Not Found'
    @Transactional
    public UserEntity updateUserTotalBalance(Long userId) {
        Optional<UserEntity> foundUserOptional = userRepository.findById(userId);

        if(foundUserOptional.isPresent()) {
            UserEntity foundUser = foundUserOptional.get();

            Optional<BigDecimal> totalBalanceOptional = userRepository.getTotalBalanceByUserEntityId(foundUser.getUserId());
            if(totalBalanceOptional.isPresent()) {
                foundUser.setTotalBalance(totalBalanceOptional.get());
            } else {
                foundUser.setTotalBalance(BigDecimal.ZERO);
            }
            updateEntity(foundUser);

            return foundUser;
        } else {
            throw new UsernameNotFoundException("Username with id " + userId + " not found!");
        }
    }

    public UserEntity findUserByUsername(String username) {
        Optional<UserEntity> foundUser = userRepository.findUserEntityByUsername(username);

        return foundUser.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }

    //TODO: Create custom exception for handling 'UserEntity Not Found'
    public UserEntity findUserByUserId(Long userId) {
        Optional<UserEntity> foundUser = userRepository.findById(userId);

        return foundUser.orElseThrow(() -> new UsernameNotFoundException("Username with id " + userId + " not found!"));
    }

    @Transactional
    public UserEntity updateEntity(UserEntity entity) {
        entity.setModificationDate(LocalDateTime.now());

        return userRepository.save(entity);
    }

}
