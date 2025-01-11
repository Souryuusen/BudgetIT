package com.soursoft.budgetit.repositories;

import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.entities.auth.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByRefreshTokenId(Long refreshTokenId);
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<List<RefreshTokenEntity>> findAllByUser(UserEntity userEntity);

    Optional<RefreshTokenEntity> deleteByRefreshTokenId(Long refreshTokenId);
    Optional<RefreshTokenEntity> deleteAllByUser(UserEntity user);

}
