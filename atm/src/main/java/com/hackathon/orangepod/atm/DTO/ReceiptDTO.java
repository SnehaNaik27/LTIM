package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ReceiptDTO {
    private LocalDateTime dateTime;
    private double availableBalance;
    private double withdrawalBalance;
    private String accountNumber;

}
