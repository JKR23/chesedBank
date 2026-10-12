package com.chesedbank.account.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Getter
@Component
public class AccountNumberGenerator {

    private final String ACCOUNT_NUMBER_LENGTH = "15";
    private final String transitNumber = "00345"; //branch's number
    private final String institutionNumber = "002";//institution's number

    public String generateAccountNumber(){

        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);//set length
        accountNumber.append(transitNumber+institutionNumber);

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 7; i++){
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }
}
