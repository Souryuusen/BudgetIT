package com.soursoft.budgetit.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtConfig {

    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    private String secret;


}
