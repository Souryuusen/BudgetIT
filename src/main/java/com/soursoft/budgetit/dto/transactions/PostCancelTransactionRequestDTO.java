package com.soursoft.budgetit.dto.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCancelTransactionRequestDTO {

    private Long transactionId;

    private String reason;

}
