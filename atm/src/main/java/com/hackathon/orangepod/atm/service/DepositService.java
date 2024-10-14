package com.hackathon.orangepod.atm.service;

import javax.security.auth.login.AccountNotFoundException;

import com.hackathon.orangepod.atm.dto.DepositRequestDto;
import com.hackathon.orangepod.atm.dto.DepositResponseDto;

public interface DepositService {

	DepositResponseDto deposit(DepositRequestDto depositRequestDto) throws AccountNotFoundException;

}
