package com.soursoft.budgetit.entities.auth;

import com.soursoft.budgetit.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_refresh_tokens")
@Data
@NoArgsConstructor @AllArgsConstructor
public class RefreshTokenEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    @ManyToOne(

    )
    @Column(name = "user_id")
    private UserEntity userId;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;



}
