package com.hackathon.orangepod.atm.service;


import com.hackathon.orangepod.atm.DTO.ATMResponse;
import com.hackathon.orangepod.atm.DTO.UserDto;
import com.hackathon.orangepod.atm.DTO.UserLoginRequest;
import com.hackathon.orangepod.atm.DTO.UserLoginResponse;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.impl.UserServiceImpl;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserTokenRepository userTokenRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

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

    @Test
    public void testCreateUser_UserAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setContact(1234567890);

        when(userRepository.existsByContact(userDto.getContact())).thenReturn(true);

        ATMResponse response = userService.createUser(userDto);

        assertEquals(AccountUtils.ACCOUNT_EXIST_CODE, response.getResponseCode());
        assertEquals(AccountUtils.ACCOUNT_EXIST_MESSAGE, response.getResponseMessage());
        assertNull(response.getAccountDto());
    }

    @Test
    public void testCreateUser_Success() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setAddress("123 Main St");
        userDto.setContact(1834567890);
        userDto.setPin(1234L);

        when(userRepository.existsByContact(userDto.getContact())).thenReturn(false);

        Account account = new Account();
        account.setBalance(0);
        when(accountService.save(any(Account.class))).thenReturn(account);

        // Call the method under test
        ATMResponse response = userService.createUser(userDto);

        // Capture the Account object passed to accountService.save
        ArgumentCaptor<Account> accountCaptor = forClass(Account.class);
        verify(accountService).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        // Assertions
        assertEquals(AccountUtils.ACCOUNT_CREATION_SUCCESS, response.getResponseCode());
        assertEquals(AccountUtils.ACCOUNT_CREATION_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getAccountDto());
        assertEquals(savedAccount.getAccountNumber(), response.getAccountDto().getAccountNumber());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        UserLoginRequest request = new UserLoginRequest();
        request.setPin(1234L);
        request.setAccountNumber(12345689L);
        when(userRepository.findUserByAccountAndPin(request.getAccountNumber(), request.getPin())).thenReturn(Optional.empty());

        UserLoginResponse response = userService.login(request);

        assertEquals("Invalid login credentials", response.getMessage());
        assertNull(response.getToken());
        assertNull(response.getUserId());
    }

    @Test
    public void testLogin_ValidCredentials_NewToken() {
        User user = new User(1L, "John Doe", "123 Main St", 1234L, 9876543210L, null, null);
        UserLoginRequest request = new UserLoginRequest();
        request.setPin(1234L);
        request.setAccountNumber(12345689L);
        when(userRepository.findUserByAccountAndPin(request.getAccountNumber(), request.getPin())).thenReturn(Optional.of(user));
        when(userTokenRepository.findByUserAndWithdrawalDate(1L, LocalDate.now())).thenReturn(null);

        UserLoginResponse response = userService.login(request);

        assertEquals("Login successful", response.getMessage());
        assertNotNull(response.getToken());
        assertEquals(1L, response.getUserId());
        verify(userTokenRepository, times(1)).save(any(UserToken.class));
    }

}