package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePinDto {

    private Long userId;
    private Long newPin;
    private int otp;

}
