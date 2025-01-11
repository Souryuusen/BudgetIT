package com.soursoft.budgetit.util;

import com.soursoft.budgetit.config.JwtConfig;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.entities.auth.RefreshTokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.soursoft.budgetit.entities.auth.RefreshTokenEntity.RefreshTokenBuilder;

@Component
public class JwtTokenProvider {

    private JwtConfig configuration;

    public JwtTokenProvider(JwtConfig configuration) {
        this.configuration = configuration;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(configuration.getSecret().getBytes());
    }

    public String generateAccessToken(UserEntity user) {
       return generateToken(user.getUsername(), configuration.getAccessTokenExpiration());
    }

    public RefreshTokenEntity generateRefreshToken(UserEntity user) {
        String generatedToken = generateToken(user.getUsername(), configuration.getRefreshTokenExpiration());
        Date expirationDate = extractExpirationDate(generatedToken);

        RefreshTokenEntity createdToken = RefreshTokenBuilder.getInstance()
            .clear()
            .withToken(generatedToken)
            .withUser(user)
            .withExpirationDate(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.of("UTC")))
            .build();

        return createdToken;
    }

    public String generateToken(String username, Long expiration) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpirationDate(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, String username) {
        return ((username.equals(extractUsername(token))) && isTokenExpired(token));
    }

}
