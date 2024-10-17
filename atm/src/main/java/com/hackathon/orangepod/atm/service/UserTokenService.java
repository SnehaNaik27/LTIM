package com.hackathon.orangepod.atm.service;

import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;

public interface UserTokenService {

    public boolean isWithdrawalLimitValid(AccountOperationRequestDTO request);

    public boolean isUserTokenValid(AccountOperationRequestDTO request);
}
