package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.transactions.PostCreateTransactionRequestDTO;
import com.soursoft.budgetit.dto.transactions.PostCreateTransactionResponseDTO;
import com.soursoft.budgetit.dto.transactions.PostProcessTransactionRequestDTO;
import com.soursoft.budgetit.dto.transactions.PostProcessTransactionResponseDTO;
import com.soursoft.budgetit.entities.AccountTransaction;
import com.soursoft.budgetit.entities.TransactionStatus;
import com.soursoft.budgetit.entities.TransactionType;
import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.services.AccountTransactionService;
import com.soursoft.budgetit.services.UserAccountService;
import com.soursoft.budgetit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.soursoft.budgetit.entities.AccountTransaction.AccountTransactionBuilder;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final UserAccountService userAccountService;
    private final UserService userService;
    private final AccountTransactionService accountTransactionService;

    @Autowired
    public TransactionController(UserAccountService userAccountService, UserService userService,
                                    AccountTransactionService accountTransactionService) {
        this.userAccountService = userAccountService;
        this.userService = userService;
        this.accountTransactionService = accountTransactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostCreateTransactionResponseDTO> createTransaction(
                                                                @RequestBody PostCreateTransactionRequestDTO request) {

        UserAccount sourceAccount = userAccountService.findAccountByAccountId(request.getSourceAccountId());
        UserAccount destinationAccount = null;
        if(request.getDestinationAccountId() != null) {
            destinationAccount = userAccountService.findAccountByAccountId(request.getDestinationAccountId());
        }
        LocalDateTime date = LocalDateTime.now();
        AccountTransaction transaction = AccountTransactionBuilder.getInstance()
            .withSourceAccount(sourceAccount)
            .withDestinationAccount(destinationAccount)
            .withType(request.getType())
            .withStatus(TransactionStatus.PENDING)
            .withTransactionValue(request.getValue())
            .withCreationDate(date)
            .withModificationDate(date)
            .build();

        transaction = accountTransactionService.saveTransaction(transaction);

        return new ResponseEntity<>(PostCreateTransactionResponseDTO.fromEntity(transaction), HttpStatus.CREATED);
    }

    @PostMapping("/process")
    public ResponseEntity<?> processTransaction(@RequestBody PostProcessTransactionRequestDTO request) {
        AccountTransaction transaction = accountTransactionService.processTransaction(request.getTransactionId());

        userService.updateUserTotalBalance(transaction.getSourceAccount().getOwner().getUserId());

        return new ResponseEntity<>(PostProcessTransactionResponseDTO.fromEntity(transaction), HttpStatus.OK);
    }

}
