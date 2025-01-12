package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.account.PostCreateAccountRequestDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @PostMapping("/create")
    public void createNewAccount(@RequestBody PostCreateAccountRequestDTO requestDTO) {

        System.out.println(requestDTO);


    }

}
