package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.entities.auth.RefreshTokenEntity;
import com.soursoft.budgetit.repositories.RefreshTokenRepository;
import com.soursoft.budgetit.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.soursoft.budgetit.entities.auth.RefreshTokenEntity.RefreshTokenBuilder;

@Service
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    private JwtTokenProvider jwtTokenProvider;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public RefreshTokenEntity saveRefreshToken(RefreshTokenEntity refreshToken) {
        refreshToken = this.refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

}
