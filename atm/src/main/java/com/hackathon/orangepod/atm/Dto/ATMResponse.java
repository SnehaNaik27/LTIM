package com.hackathon.orangepod.atm.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ATMResponse {
    private String responseCode;
    private String responseMessage;
    private List<AccountDto> accountDtos;

}