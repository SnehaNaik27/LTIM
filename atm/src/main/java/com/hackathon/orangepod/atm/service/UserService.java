package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.dto.UserLoginRequest;
import com.hackathon.orangepod.atm.dto.UserLoginResponse;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserLoginResponse login(UserLoginRequest request) {
       //Business logic to validate account and pin

        String token= UUID.randomUUID().toString();

        UserToken userToken=new UserToken();
        userToken.setAccountNumber(request.getAccountNumber());
        userToken.setToken(token);
        userTokenRepository.save(userToken);

        UserLoginResponse response=new UserLoginResponse();
        response.setToken(token);
        response.setMessage("Login successful");

        return response;

    }
}
