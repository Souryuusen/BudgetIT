package com.soursoft.budgetit.repositories;

import com.soursoft.budgetit.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {



}
