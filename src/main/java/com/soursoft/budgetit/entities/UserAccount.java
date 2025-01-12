package com.soursoft.budgetit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "user_accounts")
@Data
@NoArgsConstructor @AllArgsConstructor
public class UserAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_id")
    @Getter
    private long accountId;

    @Column(name = "acc_name")
    private String name;

    @Column(name = "acc_current_balance")
    private BigDecimal currentBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_owner_id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "sourceAccount")
    private List<AccountTransaction> sendTransactions;

    @OneToMany(mappedBy = "destinationAccount")
    private List<AccountTransaction> receiveTransactions;


}
