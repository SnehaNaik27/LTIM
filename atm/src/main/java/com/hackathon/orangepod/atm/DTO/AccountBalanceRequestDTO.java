package com.hackathon.orangepod.atm.DTO;
import lombok.Data;

@Data
public class AccountBalanceRequestDTO {

    private Long accountId;
    private double balance;
    private String token;

}
