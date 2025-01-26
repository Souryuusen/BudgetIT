package com.soursoft.budgetit.dto.transactions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soursoft.budgetit.entities.AccountTransaction;
import com.soursoft.budgetit.entities.TransactionStatus;
import com.soursoft.budgetit.entities.TransactionType;
import com.soursoft.budgetit.entities.UserAccount;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateTransactionResponseDTO {

    private Long transactionId;
    private Long sourceId;
    private Long destinationId;

    private BigDecimal transactionValue;

    private TransactionStatus status;

    private TransactionType type;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime removalDate;

    public static PostCreateTransactionResponseDTO fromEntity(AccountTransaction entity) {
        PostCreateTransactionResponseDTO response = new PostCreateTransactionResponseDTO();
        response.setTransactionId(entity.getTransactionId());
        response.setSourceId(entity.getSourceAccount().getAccountId());
        if(entity.getDestinationAccount() != null) {
            response.setDestinationId(entity.getDestinationAccount().getAccountId());
        } else {
            response.setDestinationId(null);
        }
        response.setTransactionValue(entity.getTransactionValue());
        response.setStatus(entity.getStatus());
        response.setType(entity.getType());
        response.setCreationDate(entity.getCreationDate());
        response.setModificationDate(entity.getModificationDate());
        response.setRemovalDate(entity.getRemovalDate());

        return response;
    }

}
