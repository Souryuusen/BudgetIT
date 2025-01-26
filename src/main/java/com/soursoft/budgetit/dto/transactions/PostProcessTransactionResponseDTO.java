package com.soursoft.budgetit.dto.transactions;

import com.soursoft.budgetit.entities.AccountTransaction;
import com.soursoft.budgetit.entities.TransactionStatus;
import com.soursoft.budgetit.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostProcessTransactionResponseDTO {


    private Long transactionId;
    private Long sourceId;
    private Long destinationId;

    private BigDecimal transactionValue;

    private TransactionStatus status;

    private TransactionType type;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime removalDate;

    public static PostProcessTransactionResponseDTO fromEntity(AccountTransaction transaction) {
        PostProcessTransactionResponseDTO response = new PostProcessTransactionResponseDTO();

        response.setTransactionId(transaction.getTransactionId());
        response.setSourceId(transaction.getSourceAccount().getAccountId());
        if(transaction.getDestinationAccount() != null) {
            response.setDestinationId(transaction.getDestinationAccount().getAccountId());
        } else {
            response.setDestinationId(null);
        }
        response.setTransactionValue(transaction.getTransactionValue());
        response.setStatus(transaction.getStatus());
        response.setType(transaction.getType());
        response.setCreationDate(transaction.getCreationDate());
        response.setModificationDate(transaction.getModificationDate());
        response.setRemovalDate(transaction.getRemovalDate());

        return response;
    }


}
