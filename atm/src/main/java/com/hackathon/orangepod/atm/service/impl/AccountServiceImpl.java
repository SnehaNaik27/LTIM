package com.hackathon.orangepod.atm.service.impl;

import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.DTO.DepositResponseDto;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.exceptions.InvalidTokenException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.AccountRepository;
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

    public void withdraw(AccountOperationRequestDTO requestDto) throws InvalidTokenException, InsufficientFundsException, AccountNotFoundException {
        Optional<UserToken> userToken = userTokenRepository.findByToken(requestDto.getToken());

        if (userToken.isEmpty()){
            throw new InvalidTokenException("User token is invalid or is expired. Please re-login.");
        }

        Account account = accountRepository.findById(requestDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account.getBalance() < requestDto.getAmount()) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - requestDto.getAmount());
        accountRepository.save(account);
    }


    public DepositResponseDto deposit(AccountOperationRequestDTO depositRequestDto) throws InvalidTokenException, AccountNotFoundException {
        Optional<UserToken> userToken = userTokenRepository.findByToken(depositRequestDto.getToken());

        if (userToken.isEmpty()){
            throw new InvalidTokenException("User token is invalid or is expired. Please re-login.");
        }

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

    public double getBalance(AccountOperationRequestDTO requestDTO) throws InvalidTokenException, AccountNotFoundException {
        Optional<UserToken> userToken = userTokenRepository.findByToken(requestDTO.getToken());

        if (userToken.isEmpty()){
            throw new InvalidTokenException("User token is invalid or is expired. Please re-login.");
        }

        Optional<Account> account = accountRepository.findById(requestDTO.getAccountId());

        if (account.isEmpty()){

            throw new AccountNotFoundException("Account not found");
        }

        if (account.get().getUsers().isEmpty()){
            throw new RuntimeException("User is not associated with this account");
        }

        return account.get().getBalance();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
