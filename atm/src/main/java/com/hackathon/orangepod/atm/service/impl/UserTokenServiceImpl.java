package com.hackathon.orangepod.atm.service.impl;

import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.exceptions.InvalidTokenException;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    UserTokenRepository userTokenRepository;

    @Override
    public boolean isWithdrawalLimitValid(AccountOperationRequestDTO request) {
        Optional<UserToken> userToken = userTokenRepository.findByToken(request.getToken());
        if(userToken.isPresent() &&
                request.getAmount() <= userToken.get().getWithdrawalLimit() &&
                userToken.get().getWithdrawalDate().equals(LocalDate.now())) {
            userToken.get().setWithdrawalLimit(userToken.get().getWithdrawalLimit() - request.getAmount());
            userTokenRepository.save(userToken.get());
            return true;
        }
        return false;
    }

    public boolean isUserTokenValid(AccountOperationRequestDTO request) {
        Optional<UserToken> userToken = userTokenRepository.findByToken(request.getToken());

        if (userToken.isEmpty()){
            return false;
        } else {
            return true;
        }
    }
}
