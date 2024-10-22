package com.hackathon.orangepod.atm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String address;
    private long contact;
    private long pin;
    private String email;
    private List<AccountDto> accounts;
}
