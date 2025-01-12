package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.auth.login.PostLoginRequestDTO;
import com.soursoft.budgetit.dto.auth.login.PostLoginResponseDTO;
import com.soursoft.budgetit.dto.auth.logout.PostLogoutRequestDTO;
import com.soursoft.budgetit.dto.auth.refresh.PostRefreshRequestDTO;
import com.soursoft.budgetit.dto.auth.refresh.PostRefreshResponseDTO;
import com.soursoft.budgetit.dto.auth.register.PostRegisterRequestDTO;
import com.soursoft.budgetit.dto.auth.register.PostRegisterResponseDTO;
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
    public ResponseEntity<PostLoginResponseDTO> login(@RequestBody PostLoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

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
    public ResponseEntity<PostRefreshResponseDTO> refreshToken(@RequestBody PostRefreshRequestDTO postRefreshRequestDTO) {
        Optional<RefreshTokenEntity> foundToken = refreshTokenService.findRefreshTokenByToken(postRefreshRequestDTO.getRefreshToken());

        if(foundToken.isPresent()) {
            RefreshTokenEntity tokenEntity = foundToken.get();

            UserEntity tokenUser = tokenEntity.getUser();
            if (!jwtTokenProvider.isTokenExpired(tokenEntity.getToken())) {

                String accessToken = jwtTokenProvider.generateAccessToken(tokenUser);

                RefreshTokenEntity newToken = jwtTokenProvider.generateRefreshToken(tokenUser);
                String refreshToken = newToken.getToken();

                refreshTokenService.saveRefreshToken(newToken);

                return ResponseEntity.ok(new PostRefreshResponseDTO(accessToken, refreshToken));
            } else {
                refreshTokenService.deleteAllRefreshTokensByUser(tokenUser);
            }
        }

        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/register")
    public PostRegisterResponseDTO register(@RequestBody PostRegisterRequestDTO registerDataDTO) {

        return userService.registerUser(registerDataDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody PostLogoutRequestDTO logoutRequestDTO) {
        UserEntity userEntity = userService.findUserByUsername(logoutRequestDTO.getUsername());
        refreshTokenService.deleteAllRefreshTokensByUser(userEntity);

        return ResponseEntity.ok("User logged out");

    }


}
