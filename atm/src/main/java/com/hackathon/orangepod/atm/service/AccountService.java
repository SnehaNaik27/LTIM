package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.dto.DepositRequestDto;
import com.hackathon.orangepod.atm.dto.DepositResponseDto;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void withdraw(Long accountId, Double amount) throws InsufficientFundsException, AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
    
    
	public DepositResponseDto deposit(DepositRequestDto depositRequestDto) throws AccountNotFoundException {
		 Account account = accountRepository.findById(depositRequestDto.getAccountId())
	                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

		    
	        account.setBalance(account.getBalance() + depositRequestDto.getAmount());
	        accountRepository.save(account);
	        
	        DepositResponseDto depositResponseDto = new DepositResponseDto();
	        depositResponseDto.setAccountId(account.getAccountId());
	        depositResponseDto.setAccountNumber(account.getAccountNumber());
	        depositResponseDto.setBalance(account.getBalance());
			return depositResponseDto;
	    }
		
}
