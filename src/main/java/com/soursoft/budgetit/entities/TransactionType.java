package com.soursoft.budgetit.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    CREDIT,
    DEBIT,
    TRANSFER;

    @JsonCreator
    public static TransactionType fromValue(String value) {
        return TransactionType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name().toUpperCase();
    }
}
