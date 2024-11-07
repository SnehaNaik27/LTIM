package com.hackathon.orangepod.atm.DTO;

import lombok.Data;

@Data
public class UpdatePinDto {

    private Long userId;
    private Long newPin;
    private int otp;

}
