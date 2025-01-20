package com.soursoft.budgetit.repositories;

import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findUserAccountByAccountId(Long accountId);

    Optional<UserAccount> findByOwnerAndName(UserEntity owner, String name);

    List<UserAccount> findByOwnerUserId(Long userId);

    Boolean existsByAccountId(Long accountId);
}
