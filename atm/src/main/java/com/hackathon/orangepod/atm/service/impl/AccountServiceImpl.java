package com.hackathon.orangepod.atm.service.impl;

import com.hackathon.orangepod.atm.DTO.AccountBalanceRequestDTO;
import com.hackathon.orangepod.atm.DTO.DepositRequestDto;
import com.hackathon.orangepod.atm.DTO.DepositResponseDto;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserAccount;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import com.hackathon.orangepod.atm.repository.UserAccountRepository;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountRepository userccountRepository;

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

    public double getBalance(AccountBalanceRequestDTO requestDTO){
        User user = userTokenRepository.findByToken(requestDTO.getToken());

        if (user ==null){
            throw new RuntimeException("Invalid token");
        }

        Optional<Account> account = accountRepository.findById(requestDTO.getAccountId());

        if (!account.isPresent()){

            throw new RuntimeException("Account not found");
        }

        UserAccount userAccount = userAccountRepository.findByUserAndAccount(user,account.get());
        if (userAccount==null){
            throw new RuntimeException("User is not associated with this account");
        }

        return account.get().getBalance();
    }
}
