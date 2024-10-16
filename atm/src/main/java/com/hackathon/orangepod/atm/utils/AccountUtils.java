package com.hackathon.orangepod.atm.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_CODE = "001";
    public static final String ACCOUNT_EXIST_MESSAGE = "This user already exist";

    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been Successfully created";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        //genrate random number between min and max
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        //convert the current and randomnumber to string and contact.
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        // Concatenate year and random number to form the account number
        String accountNumberStr = year + randomNumber;


        // Convert the concatenated string to a long
        return accountNumberStr;

    }
}
