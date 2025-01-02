package com.soursoft.budgetit.util;

import com.soursoft.budgetit.config.JwtConfig;
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

    public String generateToken(String username) {

        SecretKey key = Keys.hmacShaKeyFor(configuration.getSecret().getBytes());

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + configuration.getExpiration()))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

}
