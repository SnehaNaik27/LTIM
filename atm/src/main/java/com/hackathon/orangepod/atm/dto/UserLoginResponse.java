package com.hackathon.orangepod.atm.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse {

   private String token;
   private String message;
   private Long userId;
}
