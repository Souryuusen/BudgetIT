package com.soursoft.budgetit.dto.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostLoginResponseDTO {

    private String accessToken;
    private String refreshToken;

}
