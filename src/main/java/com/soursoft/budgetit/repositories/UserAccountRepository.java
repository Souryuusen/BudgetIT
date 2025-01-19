package com.soursoft.budgetit.repositories;

import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByOwnerAndName(UserEntity owner, String name);

}
