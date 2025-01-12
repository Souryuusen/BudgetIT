package com.soursoft.budgetit.dto.account;

import com.soursoft.budgetit.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateAccountRequestDTO {

    private String name;

    private BigDecimal balance;

    private UserEntity owner;

}
