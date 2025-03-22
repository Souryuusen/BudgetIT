package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.TransactionStatus;
import com.soursoft.budgetit.entities.TransactionType;
import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.repositories.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.soursoft.budgetit.entities.UserAccount.UserAccountBuilder;

@Service
public class UserAccountService {

    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    //TODO: Add Validation For Method Arguments
    public UserAccount createNewAccount(UserEntity owner, String accountName, BigDecimal startingBalance, Boolean mainAccount) {

        UserAccount foundAccount = findAccountByOwnerAndName(owner,accountName);

        if(foundAccount == null) {
            LocalDateTime creationDate = LocalDateTime.now();
            UserAccount newAccount = UserAccountBuilder.getInstance()
                .withMainAccount(mainAccount)
                .withName(accountName)
                .withCurrentBalance(startingBalance)
                .withCreationDate(creationDate)
                .withModificationDate(creationDate)
                .withRemovalDate(null)
                .withOwner(owner)
                .build();
            newAccount = userAccountRepository.save(newAccount);

            final long newAccountId = newAccount.getAccountId();

            if(mainAccount) {
                owner.getAccounts()
                    .stream()
                    .filter(account -> account.getAccountId() != newAccountId)
                    .forEach((account -> {
                        account.setMainAccount(false);
                        updateEntity(account);
                    }));
            }

            return newAccount;
        } else {
            throw new RuntimeException("User Already Have Account With Specified Name");
        }
    }

    @Transactional
    public TransactionStatus transferFunds(UserAccount source, UserAccount destination, BigDecimal value) {
        if(!isBalanceSufficient(source, value)) {
            return TransactionStatus.REJECTED;
        }

        source.setCurrentBalance(source.getCurrentBalance().subtract(value));
        destination.setCurrentBalance(destination.getCurrentBalance().add(value));

        updateEntity(source);
        updateEntity(destination);

        return TransactionStatus.FINISHED;
    }

    @Transactional
    public TransactionStatus correctAccountBalance(UserAccount account, BigDecimal value, TransactionType operation) {
        if(operation != TransactionType.CREDIT && operation != TransactionType.DEBIT) {
            throw new RuntimeException("Invalid operation type for balance correction: " + operation);
        }
        if(operation == TransactionType.DEBIT && !isBalanceSufficient(account, value)) {
            return TransactionStatus.REJECTED;
        }

        switch (operation) {
            case CREDIT -> account.setCurrentBalance(account.getCurrentBalance().add(value));
            case DEBIT -> account.setCurrentBalance(account.getCurrentBalance().subtract(value));
        }
        updateEntity(account);

        return TransactionStatus.FINISHED;
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
        return account.getCurrentBalance().compareTo(amountToCharge) >= 0;
    }

    public UserAccount updateEntity(UserAccount entity) {
        entity.setModificationDate(LocalDateTime.now());

        return userAccountRepository.save(entity);
    }


}
