package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserAccountService {

    private UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount createNewAccount(UserEntity owner, String accountName, BigDecimal startingBalance) {

        return null;
    }

    public UserAccount findAccountByOwnerAndName(UserEntity owner, String name) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findByOwnerAndName(owner, name);

        return optionalUserAccount.orElse(null);
    }


}
