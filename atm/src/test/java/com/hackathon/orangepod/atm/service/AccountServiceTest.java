package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.exceptions.WithdrawalLimitReachedException;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import com.hackathon.orangepod.atm.service.impl.AccountServiceImpl;
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

    @Mock
    private UserTokenService userTokenService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setBalance(1000.0);
    }

    AccountOperationRequestDTO getRequest() {
        return AccountOperationRequestDTO.builder()
                .userId(1L)
                .token("adjh-ashasjdh-asdjasdh")
                .amount(100L)
                .build();
    }

    @Test
    void testWithdraw_Success() throws InsufficientFundsException, AccountNotFoundException {

        when(userTokenService.isUserTokenValid(any())).thenReturn(true);
        when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.of(account));
        when(userTokenService.isWithdrawalLimitValid(any())).thenReturn(true);

        accountService.withdraw(getRequest());

        verify(accountRepository, times(1)).findAccountByUserId(1L);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        AccountOperationRequestDTO request = getRequest();
        request.setAmount(2000L);
        when(userTokenService.isUserTokenValid(any())).thenReturn(true);
        when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.of(account));
        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw(request);
        });

       // verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, never()).save(account);
    }

    @Test
    void testWithdraw_AccountNotFound() {
        when(userTokenService.isUserTokenValid(any())).thenReturn(true);
        when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            accountService.withdraw(getRequest());
        });

        verify(accountRepository, times(1)).findAccountByUserId(1L);
        verify(accountRepository, never()).save(account);
    }

    @Test
    void testWithdraw_WithdrawalLimitReached() {
        when(userTokenService.isUserTokenValid(any())).thenReturn(true);
        when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.of(account));
        when(userTokenService.isWithdrawalLimitValid(getRequest())).thenReturn(false);

        assertThrows(WithdrawalLimitReachedException.class, () -> {
            accountService.withdraw(getRequest());
        });

        verify(accountRepository, times(1)).findAccountByUserId(1L);
        verify(accountRepository, never()).save(account);
    }
}
