package com.soursoft.budgetit.dto.auth.refresh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostRefreshResponseDTO {

    private String accessToken;
    private String refreshToken;

}
