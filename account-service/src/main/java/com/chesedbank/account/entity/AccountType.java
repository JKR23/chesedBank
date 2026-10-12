package com.chesedbank.account.entity;

import lombok.Getter;

@Getter
public enum AccountType {
    CHECKING("CHECKING"),
    SAVING("SAVING"),
    CREDIT("CREDT"),
    FOREIGN_CURRENCIES("FOREIGN CURRENCIES"),
    INVESTMENTS("INVESTMENTS");

    private final String description;

    AccountType(String description){
        this.description = description;
    }
}
