package com.hackathon.orangepod.atm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.orangepod.atm.dto.DepositRequestDto;
import com.hackathon.orangepod.atm.dto.DepositResponseDto;
import com.hackathon.orangepod.atm.exception.AccountNotFoundException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.repository.DepositRepository;

@Service
public class DepositServiceImpl implements DepositService {
	
	@Autowired
	private DepositRepository depositRepository;

	@Override
	public DepositResponseDto deposit(DepositRequestDto depositRequestDto) throws AccountNotFoundException {
		 Account account = depositRepository.findById(depositRequestDto.getAccountId())
	                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

		    
	        account.setBalance(account.getBalance() + depositRequestDto.getAmount());
	        depositRepository.save(account);
	        
	        DepositResponseDto depositResponseDto = new DepositResponseDto();
	        depositResponseDto.setAccountId(account.getAccountId());
	        depositResponseDto.setAccountNumber(account.getAccountNumber());
	        depositResponseDto.setBalance(account.getBalance());
			return depositResponseDto;
	    }
		
		
		
	}


