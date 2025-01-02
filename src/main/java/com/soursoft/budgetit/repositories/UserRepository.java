package com.soursoft.budgetit.repositories;

import com.soursoft.budgetit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserEntityByUsername(String username);

}
