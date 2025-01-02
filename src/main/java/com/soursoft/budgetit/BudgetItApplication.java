package com.soursoft.budgetit;

import com.soursoft.budgetit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BudgetItApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetItApplication.class, args);
    }

}
