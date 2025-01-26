package com.soursoft.budgetit.repositories;

import com.soursoft.budgetit.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    @Query("SELECT SUM(ua.currentBalance) FROM UserAccount ua WHERE ua.owner.userId = :userId")
    Optional<BigDecimal> getTotalBalanceByUserEntityId(@Param("userId") Long userEntityId);

}
