package com.soursoft.budgetit.dto.users.userId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostChargeUserByUserIdRequestDTO {

    private Boolean dividePayment;

    private Long accountId;

    private BigDecimal amount;

}
