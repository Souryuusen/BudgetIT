package com.soursoft.budgetit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "account_transactions")
@Data
@NoArgsConstructor @AllArgsConstructor
public class AccountTransaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trn_id")
    @Getter
    private long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trn_source_acc_id")
    private UserAccount sourceAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trn_destination_acc_id")
    private UserAccount destinationAccount;

    @Column(name = "trn_value")
    private BigDecimal transactionValue;

}
