package com.hackathon.orangepod.atm.service;

<<<<<<< HEAD
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserTokenRepository userTokenRepository;


    public String logout(Long userId) {

        // Check if the token exists for the given account and is not expired


            Optional<UserToken> userTokenOpt = userTokenRepository.findTokenByUserId(userId);
            System.out.println("uuseu "+userTokenOpt.get().getToken());
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


=======
import com.hackathon.orangepod.atm.DTO.*;

import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> 6d26138d323ff2f91e560bc334ce94781825d15a

import java.util.List;
import java.util.stream.Collectors;

<<<<<<< HEAD
=======
public interface UserService {

    public ATMResponse createAccount(UserDto userDTO);

    public UserLoginResponse login(UserLoginRequest request);

    public void logout(String token);
>>>>>>> 6d26138d323ff2f91e560bc334ce94781825d15a

