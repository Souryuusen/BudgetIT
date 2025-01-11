package com.soursoft.budgetit.entities.auth;

import com.soursoft.budgetit.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Data
    public static class RefreshTokenBuilder {

        private String token;

        private LocalDateTime expirationDate;

        private UserEntity user;

        private static RefreshTokenBuilder instance;

        private RefreshTokenBuilder() {
            this.token = "";

            this.expirationDate = null;

            this.user = null;
        }

        public static RefreshTokenBuilder getInstance() {
            if(instance == null) {
                instance = new RefreshTokenBuilder();
            }

            return instance;
        }

        public RefreshTokenBuilder clear() {
            this.token = "";

            this.expirationDate = null;

            this.user = null;

            return this;
        }

        public RefreshTokenEntity build() {
            RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
            refreshTokenEntity.setToken(this.getToken());
            refreshTokenEntity.setUser(this.getUser());
            refreshTokenEntity.setExpirationDate(this.getExpirationDate());

            return refreshTokenEntity;
        }

        public RefreshTokenBuilder withToken(String token) {
            this.setToken(token);

            return this;
        }

        public RefreshTokenBuilder withUser(UserEntity user) {
            this.setUser(user);

            return this;
        }

        public RefreshTokenBuilder withExpirationDate(LocalDateTime expirationDate) {
            this.setExpirationDate(expirationDate);

            return this;
        }


    }

}
