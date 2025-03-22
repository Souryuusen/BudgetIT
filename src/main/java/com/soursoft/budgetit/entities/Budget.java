package com.soursoft.budgetit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user_budgets")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Budget {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "bdg_id")
    private Long budgetId;

    @Column (name = "bdg_name", nullable = false)
    private String name;

    @Column (name = "bdg_current_amount")
    private BigDecimal currentAmount;

    @OneToOne(mappedBy = "id", fetch = FetchType.LAZY)
    @JoinColumn(name = "bdg_config_id", nullable = false)
    private BudgetConfig config;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bdg_owner_id", nullable = false)
    private UserEntity owner;

}
