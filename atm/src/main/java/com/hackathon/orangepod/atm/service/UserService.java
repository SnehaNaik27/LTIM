package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.*;

import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {

    public ATMResponse createAccount(UserDto userDTO);

    public UserLoginResponse login(UserLoginRequest request);

    public void logout(String token);

}
