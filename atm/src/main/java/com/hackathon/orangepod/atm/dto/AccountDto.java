package com.hackathon.orangepod.atm.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private String accountNumber;
    private double balance;
    private Long accountId;
}
