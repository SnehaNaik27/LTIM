package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.DTO.DepositResponseDto;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;

public interface AccountService {

    public void withdraw(AccountOperationRequestDTO depositRequestDto) throws InsufficientFundsException, AccountNotFoundException;

	public DepositResponseDto deposit(AccountOperationRequestDTO depositRequestDto) throws AccountNotFoundException;

	public double getBalance(AccountOperationRequestDTO requestDTO);

	public Account save(Account account);

}
