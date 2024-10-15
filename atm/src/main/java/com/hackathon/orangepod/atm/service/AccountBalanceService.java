package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.AccountBalanceRequestDTO;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserAccount;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import com.hackathon.orangepod.atm.repository.UserAccountRepository;
import com.hackathon.orangepod.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBalanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public double getBalance(AccountBalanceRequestDTO requestDTO){
        User user =userRepository.findByToken(requestDTO.getToken());

        if (user ==null){
            throw new RuntimeException("Invalid token");
        }

        Account account =accountRepository.findByAccountNumber(requestDTO.getAccountNumber());

        if (account ==null){

            throw new RuntimeException("Account not found");
        }

        UserAccount userAccount = userAccountRepository.findByUserAndAccount(user,account);
        if (userAccount==null){
            throw new RuntimeException("User is not associated with this account");
        }

        return account.getBalance();
    }
}
