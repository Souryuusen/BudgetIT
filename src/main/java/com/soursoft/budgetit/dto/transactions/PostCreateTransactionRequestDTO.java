package com.soursoft.budgetit.dto.transactions;

import com.soursoft.budgetit.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateTransactionRequestDTO {

    private Long sourceAccountId;
    private Long destinationAccountId;

    private BigDecimal value;

    private TransactionType type;

}
