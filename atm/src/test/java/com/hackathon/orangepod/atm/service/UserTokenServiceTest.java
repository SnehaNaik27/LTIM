package com.hackathon.orangepod.atm.service;



import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.impl.UserTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTokenServiceTest {

    @Mock
    private UserTokenRepository userTokenRepository;

    @InjectMocks
    private UserTokenServiceImpl userTokenService;

    private AccountOperationRequestDTO request;
    private UserToken userToken;

    @BeforeEach
    public void setUp() {
        request = AccountOperationRequestDTO.builder()
                .token("validToken")
                .amount(100).build();

        userToken = new UserToken();
        userToken.setToken("validToken");
        userToken.setWithdrawalLimit(200);
        userToken.setWithdrawalDate(LocalDate.now());
    }

    @Test
    public void testIsWithdrawalLimitValid_ValidTokenAndLimit() {
        when(userTokenRepository.findByToken("validToken")).thenReturn(Optional.of(userToken));

        boolean result = userTokenService.isWithdrawalLimitValid(request);

        assertTrue(result);
        assertEquals(100, userToken.getWithdrawalLimit());
        verify(userTokenRepository, times(1)).save(userToken);
    }

    @Test
    public void testIsWithdrawalLimitValid_InvalidToken() {
        when(userTokenRepository.findByToken("invalidToken")).thenReturn(Optional.empty());

        request.setToken("invalidToken");
        boolean result = userTokenService.isWithdrawalLimitValid(request);

        assertFalse(result);
        verify(userTokenRepository, never()).save(any(UserToken.class));
    }

    @Test
    public void testIsWithdrawalLimitValid_ExceedsLimit() {
        userToken.setWithdrawalLimit(50);
        when(userTokenRepository.findByToken("validToken")).thenReturn(Optional.of(userToken));

        boolean result = userTokenService.isWithdrawalLimitValid(request);

        assertFalse(result);
        verify(userTokenRepository, never()).save(any(UserToken.class));
    }

    @Test
    public void testIsWithdrawalLimitValid_InvalidDate() {
        userToken.setWithdrawalDate(LocalDate.now().minusDays(1));
        when(userTokenRepository.findByToken("validToken")).thenReturn(Optional.of(userToken));

        boolean result = userTokenService.isWithdrawalLimitValid(request);

        assertFalse(result);
        verify(userTokenRepository, never()).save(any(UserToken.class));
    }

    @Test
    public void testIsUserTokenValid_ValidToken() {
        when(userTokenRepository.findByToken("validToken")).thenReturn(Optional.of(userToken));

        boolean result = userTokenService.isUserTokenValid(request);

        assertTrue(result);
    }

    @Test
    public void testIsUserTokenValid_InvalidToken() {
        when(userTokenRepository.findByToken("invalidToken")).thenReturn(Optional.empty());

        request.setToken("invalidToken");
        boolean result = userTokenService.isUserTokenValid(request);

        assertFalse(result);
    }
}
