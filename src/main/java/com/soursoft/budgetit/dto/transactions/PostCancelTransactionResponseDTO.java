package com.soursoft.budgetit.dto.transactions;

import com.soursoft.budgetit.entities.AccountTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCancelTransactionResponseDTO {

    private AccountTransaction transaction;

    public static PostCancelTransactionResponseDTO fromEntity(AccountTransaction transaction) {
        return new PostCancelTransactionResponseDTO(transaction);
    }

}
