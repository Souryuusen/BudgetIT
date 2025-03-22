package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.accounts.PostCreateAccountRequestDTO;
import com.soursoft.budgetit.dto.accounts.PostCreateAccountResponseDTO;
import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.services.UserAccountService;
import com.soursoft.budgetit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final UserAccountService userAccountService;
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService, UserAccountService userAccountService) {
        this.userService = userService;
        this.userAccountService = userAccountService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<PostCreateAccountResponseDTO> createNewAccount(@RequestBody PostCreateAccountRequestDTO request) {
        UserEntity foundUser = userService.findUserByUserId(request.getOwnerId());

        if(foundUser != null && foundUser.getActive()) {
            UserAccount createdAccount = userAccountService.createNewAccount(foundUser,request.getName(),
                                            request.getBalance(), request.getMainAccount());
            userService.updateUserTotalBalance(foundUser.getUserId());

            return new ResponseEntity<>(PostCreateAccountResponseDTO.fromEntity(createdAccount), HttpStatus.CREATED);
        } else {
            if(foundUser != null) {
                throw new RuntimeException("Cannot create new account for non-active user!");
            } else {
                throw new RuntimeException("User with id " + request.getOwnerId() + " has not been found");
            }
        }

    }

}
