package com.soursoft.budgetit.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor @AllArgsConstructor
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Getter
    private long userId;

    @Column(name = "user_active")
    private Boolean active;

    @Column(name = "user_username")
    private String username;
    @Column(name = "user_pwd_hash")
    private String passwordHash;

    @Column(name = "user_creation_date")
    @Getter
    private LocalDateTime creationDate;
    @Column(name = "user_modification_date")
    @Getter
    private LocalDateTime modificationDate;
    @Column(name = "user_remove_date")
    @Getter
    private LocalDateTime removeDate;

}
