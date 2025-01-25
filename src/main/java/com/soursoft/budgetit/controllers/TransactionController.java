package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.transactions.PostCreateTransactionRequestDTO;
import com.soursoft.budgetit.entities.AccountTransaction;
import com.soursoft.budgetit.entities.TransactionStatus;
import com.soursoft.budgetit.entities.TransactionType;
import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.soursoft.budgetit.entities.AccountTransaction.AccountTransactionBuilder;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final UserAccountService userAccountService;

    @Autowired
    public TransactionController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public ResponseEntity<?> processTransaction(@RequestBody PostCreateTransactionRequestDTO request) {

        UserAccount sourceAccount = userAccountService.findAccountByAccountId(request.getSourceAccountId());
        UserAccount destinationAccount = null;
        if(request.getDestinationAccountId() != null) {
            destinationAccount = userAccountService.findAccountByAccountId(request.getDestinationAccountId());
        }


        switch(request.getType()) {
            case CREDIT, DEBIT -> {
                AccountTransaction transaction = AccountTransactionBuilder.getInstance()
                    .withSourceAccount(sourceAccount)
                    .withDestinationAccount(null)
                    .withTransactionValue(request.getValue())
                    .withStatus(TransactionStatus.PENDING)
                    .withType(request.getType())
                    .build();


            }
            case TRANSFER -> {

            }

            default -> throw new RuntimeException("Transaction type" + request.getType() + " not recognized!");
        }



        return null;
    }


}
