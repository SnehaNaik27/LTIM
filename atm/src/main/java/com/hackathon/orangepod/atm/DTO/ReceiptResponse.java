package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ReceiptResponse {
    private LocalDateTime dateTime;
    private double availableBalance;
    private double withdrawalBalance;
    private String accountNumber;

}
