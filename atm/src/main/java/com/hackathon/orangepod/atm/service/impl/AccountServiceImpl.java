package com.hackathon.orangepod.atm.service.impl;

import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import com.hackathon.orangepod.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

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
}
