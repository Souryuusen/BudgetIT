package com.soursoft.budgetit.services;

import com.soursoft.budgetit.entities.AccountTransaction;
import com.soursoft.budgetit.entities.TransactionStatus;
import com.soursoft.budgetit.entities.TransactionType;
import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.repositories.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountTransactionService {

    private final AccountTransactionRepository repository;

    private final UserAccountService userAccountService;


    @Autowired
    public AccountTransactionService(AccountTransactionRepository repository, UserAccountService userAccountService) {
        this.repository = repository;
        this.userAccountService = userAccountService;
    }

    // TODO: Create exceptions to handle method logic instead of RuntimeException
    public AccountTransaction processTransaction(Long transactionId) {
        final AccountTransaction transaction = findAccountTransactionById(transactionId);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Only transaction with status 'Pending' can be processed");
        }
        TransactionStatus status;

        UserAccount sourceAccount = transaction.getSourceAccount();
        UserAccount destinationAccount = transaction.getDestinationAccount();
        if (transaction.getType() == TransactionType.TRANSFER) {

            status = userAccountService.transferFunds(sourceAccount, destinationAccount,
                transaction.getTransactionValue());
        } else {
            status = userAccountService.correctAccountBalance(sourceAccount, transaction.getTransactionValue(),
                transaction.getType());
        }
        transaction.setStatus(status);

        return updateEntity(transaction);
    }

    public AccountTransaction cancelTransaction(Long transactionId) {
        final AccountTransaction transaction = findAccountTransactionById(transactionId);
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Only transaction with status 'Pending' can be processed");
        }

        transaction.setStatus(TransactionStatus.CANCELED);

        return updateEntity(transaction);
    }

    // TODO: Create custom exception for handling transaction not found by ID
    public AccountTransaction findAccountTransactionById(Long transactionId) {
        Optional<AccountTransaction> accountTransactionOptional = repository.findById(transactionId);

        return accountTransactionOptional.orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public AccountTransaction saveTransaction(AccountTransaction transaction) {

        return this.repository.save(transaction);
    }

    public AccountTransaction updateEntity(AccountTransaction entity) {
        entity.setModificationDate(LocalDateTime.now());

        return this.repository.save(entity);
    }

}
