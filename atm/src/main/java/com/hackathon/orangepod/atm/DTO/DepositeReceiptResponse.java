package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DepositeReceiptResponse {
    private LocalDateTime dateTime;
    private double availableBalance;
    private double depositBalance;
    private String accountNumber;

}
