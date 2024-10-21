package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ReceiptDTO {
    private LocalDateTime dateTime;
    private String accountName;
    private double availableBalance;
    private double withdrawalBalance;

    // Constructor
    public ReceiptDTO(LocalDateTime dateTime, String accountName, double availableBalance, double withdrawalBalance) {
        this.dateTime = dateTime;
        this.accountName = accountName;
        this.availableBalance = availableBalance;
        this.withdrawalBalance = withdrawalBalance;
    }
}
