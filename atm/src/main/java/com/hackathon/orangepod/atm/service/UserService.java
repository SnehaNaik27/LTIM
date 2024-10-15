package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.UserLogoutRequest;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    //@Autowired
    UserTokenRepository userTokenRepository;

    public UserService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public String logout(Long userId) {

        // Check if the token exists for the given account and is not expired

        try {
            Optional<UserToken> userTokenOpt = userTokenRepository.findByTokenByUserId(userId);
            System.out.println("uuseu "+userTokenOpt.get().getToken());
            if (userTokenOpt.isPresent()) {
                UserToken userToken = userTokenOpt.get();

                // Mark the token as expired
                userToken.setExpired(true);
                System.out.println("before update status as true");
                userTokenRepository.save(userToken);
                System.out.println("after update status as true");
                return "Logout successful";
            } else {
                System.out.println("inside if");
                return "Invalid token or account number";
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("exception occure while fetching the data"+ e.getMessage());

            return e.getMessage();
        }

    }


}
