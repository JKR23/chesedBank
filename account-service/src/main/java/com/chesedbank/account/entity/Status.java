package com.chesedbank.account.entity;

public enum Status {
    PENDING,     // just created, awaiting activation
    ACTIVE,      // normal, fully usable
    BLOCKED,     // temporarily disabled
    CLOSED       // permanently terminated
}
