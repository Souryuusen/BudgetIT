package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.auth.PostLoginResponseDTO;
import com.soursoft.budgetit.dto.auth.PostRefreshRequestDTO;
import com.soursoft.budgetit.dto.auth.PostRegisterRequestDTO;
import com.soursoft.budgetit.dto.auth.PostRegisterResponseDTO;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.entities.auth.RefreshTokenEntity;
import com.soursoft.budgetit.services.RefreshTokenService;
import com.soursoft.budgetit.services.UserService;
import com.soursoft.budgetit.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenService refreshTokenService;

    private UserService userService;

    public AuthController(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider, UserService userService,
                            RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<PostLoginResponseDTO> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserEntity foundUser = userService.findUserByUsername(username);

        refreshTokenService.deleteAllRefreshTokensByUser(foundUser);

        String accessToken = jwtTokenProvider.generateAccessToken(foundUser);
        RefreshTokenEntity refreshTokenEntity = jwtTokenProvider.generateRefreshToken(foundUser);
        String refreshToken = refreshTokenEntity.getToken();

        refreshTokenService.saveRefreshToken(refreshTokenEntity);

        return ResponseEntity.ok(new PostLoginResponseDTO(accessToken, refreshToken));
    }

    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<PostLoginResponseDTO> refreshToken(@RequestBody PostRefreshRequestDTO postRefreshRequestDTO) {
        Optional<RefreshTokenEntity> foundToken = refreshTokenService.findRefreshTokenByToken(postRefreshRequestDTO.getRefreshToken());

        if(foundToken.isPresent()) {
            RefreshTokenEntity tokenEntity = foundToken.get();
            if (!jwtTokenProvider.isTokenExpired(tokenEntity.getToken())) {
                UserEntity tokenUser = tokenEntity.getUser();

                String accessToken = jwtTokenProvider.generateAccessToken(tokenUser);

                RefreshTokenEntity newToken = jwtTokenProvider.generateRefreshToken(tokenUser);
                String refreshToken = newToken.getToken();

                refreshTokenService.saveRefreshToken(newToken);

                return ResponseEntity.ok(new PostLoginResponseDTO(accessToken, refreshToken));
            }
        }

        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("register")
    public PostRegisterResponseDTO register(@RequestBody PostRegisterRequestDTO registerData) {
        userService.registerUser(registerData);


        return userService.registerUser(registerData);
    }


}
