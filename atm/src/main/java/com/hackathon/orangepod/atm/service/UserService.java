package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.UserLogoutRequest;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    UserLogoutRequest userLogoutRequest;

    public String logout(UserLogoutRequest logoutRequest) {

        String accountNumber = logoutRequest.getAccountNumber();
        String token = logoutRequest.getToken();

        // Check if the token exists for the given account and is not expired

        Optional<UserToken> userTokenOpt = userTokenRepository.findByAccountNumberAndToken(accountNumber, token);

        if (userTokenOpt.isPresent()) {
            UserToken userToken = userTokenOpt.get();
            // Mark the token as expired
            userToken.setExpired(true);
            userTokenRepository.save(userToken);
            return "Logout successful";
        } else {
            return "Invalid token or account number";
        }
    }

}
