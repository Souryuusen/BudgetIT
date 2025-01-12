package com.soursoft.budgetit.dto.auth.register;

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
