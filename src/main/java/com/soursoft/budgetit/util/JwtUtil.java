package com.soursoft.budgetit.util;

import com.soursoft.budgetit.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private JwtConfig configuration;

    public JwtUtil(JwtConfig configuration) {
        this.configuration = configuration;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(configuration.getSecret().getBytes());
    }



    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + configuration.getExpiration()))
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

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, String username) {
        return ((username.equals(extractUsername(token))) && isTokenExpired(token));
    }

}
