package com.hackathon.orangepod.atm.service;


import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserTokenRepository userTokenRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogoutSuccess() {
        // Arrange
        Long userId = 1L;
        UserToken userToken = new UserToken();
        userToken.setExpired(false);

        when(userTokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(userToken));

        String result = userService.logout(userId);

        // Assert
        assertEquals("Logout successful", result);
        verify(userTokenRepository, times(1)).save(userToken);
        assertTrue(userToken.isExpired());
    }

    @Test
    public void testLogoutFailure() {
        // Arrange
        Long userId = 1L;

        when(userTokenRepository.findTokenByUserId(userId)).thenReturn(Optional.empty());

        String result = userService.logout(userId);

        // Assert
        assertEquals("Invalid token", result);
        verify(userTokenRepository, never()).save(any());
    }
}