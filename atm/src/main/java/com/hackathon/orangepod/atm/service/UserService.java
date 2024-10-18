package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.*;

import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {

   // public ATMResponse createUser(UserDto userDTO);

    public UserLoginResponse login(UserLoginRequest request);

    public String   checkPin(UserLoginRequest request);

    public boolean validateLogin(UserLoginRequest request);

    public String logout(Long userId);

    public boolean validateContactNumber(UserDto request);
}

