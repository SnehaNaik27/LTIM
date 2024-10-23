package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetBalanceReceiptResponse {
    private LocalDateTime dateTime;
    private double availableBalance;
    private String accountNumber;

}
