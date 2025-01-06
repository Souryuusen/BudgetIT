package com.soursoft.budgetit.controllers;

import com.soursoft.budgetit.dto.PostRegisterRequestDTO;
import com.soursoft.budgetit.dto.PostRegisterResponseDTO;
import com.soursoft.budgetit.services.UserService;
import com.soursoft.budgetit.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public String postLogin() {
//
//        return "";
//    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtil.generateToken(username);
    }

    @PostMapping("register")
    public PostRegisterResponseDTO register(@RequestBody PostRegisterRequestDTO registerData) {
        userService.registerUser(registerData);


        return userService.registerUser(registerData);
    }


}
