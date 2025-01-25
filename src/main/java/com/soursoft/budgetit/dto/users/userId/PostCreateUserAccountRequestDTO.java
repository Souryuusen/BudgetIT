package com.soursoft.budgetit.dto.users.userId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateUserAccountRequestDTO {

    private Boolean mainAccount;

    private String name;

    private BigDecimal balance;

}
