package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountOperationRequestDTO {

    private Long userId;
    private double amount;
    private String token;

}
