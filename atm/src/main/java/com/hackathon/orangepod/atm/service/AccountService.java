package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.AccountDto;
import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;

public interface AccountService {

    public AccountDto withdraw(AccountOperationRequestDTO depositRequestDto) throws InsufficientFundsException, AccountNotFoundException;

	public AccountDto deposit(AccountOperationRequestDTO depositRequestDto) throws AccountNotFoundException;

	public double getBalance(AccountOperationRequestDTO requestDTO);

	public Account save(Account account);


}
