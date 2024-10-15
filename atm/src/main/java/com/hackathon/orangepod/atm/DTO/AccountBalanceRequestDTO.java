package com.hackathon.orangepod.atm.DTO;
import lombok.Data;

@Data
public class AccountBalanceRequestDTO {

    private String accountNumber;
    private double balance;
    private String token;

}
