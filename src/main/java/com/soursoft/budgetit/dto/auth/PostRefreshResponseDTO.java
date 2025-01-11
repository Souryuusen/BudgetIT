package com.soursoft.budgetit.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostRefreshResponseDTO {

    private String accessToken;
    private String refreshToken;

}
