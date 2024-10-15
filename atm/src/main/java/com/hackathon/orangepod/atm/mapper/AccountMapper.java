package com.hackathon.orangepod.atm.mapper;

import com.hackathon.orangepod.atm.DTO.AccountDto;
import com.hackathon.orangepod.atm.model.Account;

public class AccountMapper {

    public static AccountDto mapAccountToDto(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountId(account.getAccountId())
                .build();

    }
}
