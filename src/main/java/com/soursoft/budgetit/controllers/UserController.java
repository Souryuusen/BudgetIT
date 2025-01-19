package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.users.userId.GetUserByIdResponseDTO;
import com.soursoft.budgetit.dto.users.userId.PostCreateUserAccountRequestDTO;
import com.soursoft.budgetit.dto.users.userId.PostCreateUserAccountResponseDTO;
import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import com.soursoft.budgetit.services.UserAccountService;
import com.soursoft.budgetit.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserAccountService userAccountService;

    public UserController(UserService userService, UserAccountService userAccountService) {
        this.userService = userService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/{userId}")
    @Transactional
    public ResponseEntity<GetUserByIdResponseDTO> getUserById(@PathVariable Long userId) {
        UserEntity foundUser = userService.findUserByUserId(userId);
        return ResponseEntity.ok(GetUserByIdResponseDTO.fromEntity(foundUser));
    }

    //TODO: Create custom exception for handling "User Not Active" situation
    //TODO: Create custom exception for handling "Account with name exist" situation
    @PostMapping("/{userId}/accounts/create")
    public ResponseEntity<PostCreateUserAccountResponseDTO> createNewAccountForUserById(@PathVariable Long userId,
                                                            @RequestBody PostCreateUserAccountRequestDTO request) {
        UserEntity foundUser = userService.findUserByUserId(userId);

        if(foundUser != null && foundUser.getActive()) {
            UserAccount foundAccount = userAccountService.findAccountByOwnerAndName(foundUser, request.getName());
            if(foundAccount == null) {
                UserAccount newAccount = userAccountService.createNewAccount(foundUser, request.getName(), request.getBalance());

                return new ResponseEntity<>(PostCreateUserAccountResponseDTO.fromEntity(newAccount), HttpStatus.CREATED);
            } else {
                throw new RuntimeException("User Already Have Account With Specified Name");
            }
        } else {
            if(foundUser != null) {
                throw new RuntimeException("Cannot create new account for non-active user!");
            } else {
                throw new RuntimeException("User with id " + userId + " has not been found");
            }
        }
    }

//    public ResponseEntity<?>

}
