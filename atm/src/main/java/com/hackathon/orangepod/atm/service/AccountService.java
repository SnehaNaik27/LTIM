package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.AccountBalanceRequestDTO;
import com.hackathon.orangepod.atm.DTO.DepositRequestDto;
import com.hackathon.orangepod.atm.DTO.DepositResponseDto;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserAccount;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import com.hackathon.orangepod.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AccountService {

    public void withdraw(Long accountId, Double amount) throws InsufficientFundsException, AccountNotFoundException;


	public DepositResponseDto deposit(DepositRequestDto depositRequestDto) throws AccountNotFoundException;

	public double getBalance(AccountBalanceRequestDTO requestDTO);

}
