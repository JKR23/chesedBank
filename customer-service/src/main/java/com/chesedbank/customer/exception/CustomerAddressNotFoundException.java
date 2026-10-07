package com.chesedbank.customer.exception;

public class CustomerAddressNotFoundException extends RuntimeException {
    public CustomerAddressNotFoundException(String message) {
        super(message);
    }
}
