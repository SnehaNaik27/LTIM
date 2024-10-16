package com.hackathon.orangepod.atm.DTO;

import lombok.Data;

@Data
public class AccountBalanceRequestDto {

    private Long accountId;
    private String token;

}
