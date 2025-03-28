package com.soursoft.budgetit.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soursoft.budgetit.entities.auth.RefreshTokenEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Getter
    private Long userId;
    @Column(name = "user_main_account_id")
    private Long mainAccountId;

    @Column(name = "user_active")
    private Boolean active;

    @Column(name = "user_username")
    private String username;
    @Column(name = "user_pwd_hash")
    private String passwordHash;

    @Column(name = "user_total_balance")
    private BigDecimal totalBalance;

    @Column(name = "user_creation_date")
    @Getter
    private LocalDateTime creationDate;
    @Column(name = "user_modification_date")
    @Getter
    private LocalDateTime modificationDate;
    @Column(name = "user_removal_date")
    @Getter
    private LocalDateTime removalDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAccount> accounts;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshTokenEntity> refreshTokens;

    @Data
    public static class UserEntityBuilder {

        private Boolean active;

        private String username;
        private String passwordHash;

        private BigDecimal totalBalance;

        private LocalDateTime creationDate;
        private LocalDateTime modificationDate;
        private LocalDateTime removalDate;

        public static UserEntityBuilder instance;

        private UserEntityBuilder() {
            this.active = null;

            this.username = null;
            this.passwordHash = null;

            this.totalBalance = null;

            this.creationDate = null;
            this.modificationDate = null;
            this.removalDate = null;
        }

        public static UserEntityBuilder getInstance() {
            if(instance == null) {
                instance = new UserEntityBuilder();
            }
            return instance;
        }

        public UserEntityBuilder clear() {
            this.setActive(null);

            this.setUsername(null);
            this.setPasswordHash(null);

            this.setTotalBalance(null);

            this.setCreationDate(null);
            this.setModificationDate(null);
            this.setRemovalDate(null);

            return this;
        }

        public UserEntity build() {
            UserEntity userEntity = new UserEntity();

            userEntity.setActive(this.getActive());

            userEntity.setUsername(this.getUsername());
            userEntity.setPasswordHash(this.getPasswordHash());

            userEntity.setTotalBalance(this.getTotalBalance());

            userEntity.setCreationDate(this.getCreationDate());
            userEntity.setModificationDate(this.getCreationDate());
            userEntity.setRemovalDate(this.getRemovalDate());

            return userEntity;
        }

        public UserEntityBuilder withActive(Boolean active) {
            this.setActive(active);

            return this;
        }

        public UserEntityBuilder withUsername(String username) {
            this.setUsername(username);

            return this;
        }

        public UserEntityBuilder withPassword(String passwordHash) {
            this.setPasswordHash(passwordHash);

            return this;
        }

        public UserEntityBuilder withTotalBalance(BigDecimal totalBalance) {
            this.setTotalBalance(totalBalance);

            return this;
        }

        public UserEntityBuilder withCreationDate(LocalDateTime creationDate) {
            this.setCreationDate(creationDate);

            return this;
        }

        public UserEntityBuilder withModificationDate(LocalDateTime modificationDate) {
            this.setModificationDate(modificationDate);

            return this;
        }

        public UserEntityBuilder withRemovalDate(LocalDateTime removalDate) {
            this.setRemovalDate(removalDate);

            return this;
        }

    }

}
