package com.soursoft.budgetit.dto.accounts;

import com.soursoft.budgetit.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateAccountRequestDTO {

    public Boolean mainAccount;

    private String name;

    private BigDecimal balance;

    private long ownerId;

}
