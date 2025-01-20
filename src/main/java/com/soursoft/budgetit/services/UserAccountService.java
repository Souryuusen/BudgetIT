package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional
    public BigDecimal calculateTotalBalanceByUserId(Long userId) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerUserId(userId);

        if(!userAccounts.isEmpty()) {
            BigDecimal totalBalance = userAccounts.stream()
                .map(UserAccount::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            return totalBalance;
        } else {
            return BigDecimal.ZERO;
        }
    }

    //TODO: Add proper exception for handling 'Account not found by Account Id'
    public UserAccount findAccountByAccountId(Long accountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findUserAccountByAccountId(accountId);

        return optionalUserAccount.orElseThrow(() -> new RuntimeException("Account not found by ID"));
    }

    public UserAccount findAccountByOwnerAndName(UserEntity owner, String name) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findByOwnerAndName(owner, name);

        return optionalUserAccount.orElse(null);
    }

    public Boolean isValidUserAccount(Long userAccountId) {
        if(userAccountId == null || userAccountId <= 0) {
            return false;
        }

        return userAccountRepository.existsByAccountId(userAccountId);
    }

    public Boolean isBalanceSufficient(UserAccount account, BigDecimal amountToCharge) {
        return account.getCurrentBalance().min(amountToCharge).compareTo(BigDecimal.ZERO) >= 0;
    }


}
