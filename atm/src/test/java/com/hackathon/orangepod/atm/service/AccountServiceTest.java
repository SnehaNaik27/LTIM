package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setBalance(1000.0);
    }

    @Test
    void testWithdraw_Success() throws InsufficientFundsException, AccountNotFoundException {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.withdraw(1L, 500.0);

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw(1L, 1500.0);
        });

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, never()).save(account);
    }

    @Test
    void testWithdraw_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            accountService.withdraw(1L, 500.0);
        });

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, never()).save(account);
    }
}
