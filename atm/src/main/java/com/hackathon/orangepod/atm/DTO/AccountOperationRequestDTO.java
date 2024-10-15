package com.hackathon.orangepod.atm.DTO;

import lombok.Data;

@Data
public class AccountOperationRequestDTO {

    private Long accountId;
    private double amount;
    private String token;

}
