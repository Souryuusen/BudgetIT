package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.soursoft.budgetit.entities.UserAccount.UserAccountBuilder;

@Service
public class UserAccountService {

    private UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    //TODO: Add Validation For Method Arguments
    public UserAccount createNewAccount(UserEntity owner, String accountName, BigDecimal startingBalance) {
        LocalDateTime creationDate = LocalDateTime.now();
        UserAccount newAccount = UserAccountBuilder.getInstance()
            .withName(accountName)
            .withCurrentBalance(startingBalance)
            .withCreationDate(creationDate)
            .withModificationDate(creationDate)
            .withRemovalDate(null)
            .withOwner(owner)
            .build();

        newAccount = userAccountRepository.save(newAccount);

        return newAccount;
    }

    public UserAccount findAccountByOwnerAndName(UserEntity owner, String name) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findByOwnerAndName(owner, name);

        return optionalUserAccount.orElse(null);
    }


}
