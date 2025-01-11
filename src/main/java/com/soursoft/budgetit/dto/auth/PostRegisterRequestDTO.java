package com.soursoft.budgetit.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRegisterRequestDTO {

    private String username;
    private String password;

}
