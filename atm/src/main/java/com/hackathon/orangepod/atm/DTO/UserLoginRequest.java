package com.hackathon.orangepod.atm.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

    private Long cardNumber;
    private Long pin;
}
