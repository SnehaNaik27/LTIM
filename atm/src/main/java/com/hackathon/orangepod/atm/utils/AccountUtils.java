package com.hackathon.orangepod.atm.utils;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_CODE = "001";
    public static final String ACCOUNT_EXIST_MESSAGE = "This user already exist";

    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been Successfully created";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        //generate random number between min and max
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        //convert the current and random number to string and contact.
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        // Concatenate year and random number to form the account number
        return year + randomNumber;

    }

    public static String generateCardNumber(){

        StringBuilder cardNumber =new StringBuilder();
        for(int i =0;i<16;i++){

            int digit = (int)(Math.random() * 10);
            cardNumber.append(digit);
        }

        return cardNumber.toString();
    }

    public static String generateCVV(){

        StringBuilder cvv = new StringBuilder();
        for(int i=0; i<3;i++){

            int digit = (int)(Math.random() * 3);
            cvv.append(digit);

        }

        return cvv.toString();
    }

    public static LocalDate calculateExpiryDate(LocalDate issueDate){

        return issueDate;
    }

    public static String maskNumber(String accountNumber) {
        int length = accountNumber.length();
        if (length <= 4) {
            return accountNumber; // No masking if account number is too short
        }
        StringBuilder masked = new StringBuilder();
        masked.append("*".repeat(length - 4));
        masked.append(accountNumber.substring(length - 4));
        return masked.toString();
    }
}
