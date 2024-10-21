package com.hackathon.orangepod.atm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hackathon.orangepod.atm.DTO.AccountDto;
import com.hackathon.orangepod.atm.DTO.AccountOperationRequestDTO;
import com.hackathon.orangepod.atm.exceptions.AccountNotFoundException;
import com.hackathon.orangepod.atm.exceptions.InsufficientFundsException;
import com.hackathon.orangepod.atm.exceptions.InvalidTokenException;
import com.hackathon.orangepod.atm.exceptions.WithdrawalLimitReachedException;
import com.hackathon.orangepod.atm.mapper.AccountMapper;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.repository.AccountRepository;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.service.AccountService;
import com.hackathon.orangepod.atm.service.UserTokenService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private EmailService emailService;

	public AccountDto withdraw(AccountOperationRequestDTO requestDto) throws InvalidTokenException,
			InsufficientFundsException, AccountNotFoundException, WithdrawalLimitReachedException {

		if (!userTokenService.isUserTokenValid(requestDto)) {
			throw new InvalidTokenException("User token is invalid or is expired. Please re-login.");
		}

		// get account from user
		Optional<Account> account = accountRepository.findAccountByUserId(requestDto.getUserId());
		if (account.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}

		Optional<User> userList = userRepository.findByAccountNumber(Long.parseLong(account.get().getAccountNumber()));

		if (account.get().getBalance() < requestDto.getAmount()) {
			String emailMessage = "Insufficient funds to withdraw ₹" + requestDto.getAmount() + ". Your balance is ₹"
					+ account.get().getBalance() + ".";
			sendEmailNotification(account, requestDto, "withdraw", emailMessage, userList);
			throw new InsufficientFundsException("Insufficient funds");

		}

		if (!userTokenService.isWithdrawalLimitValid(requestDto)) {
			String emailMessage = "Your today's withdrawal limit is reached for ₹" + requestDto.getAmount()
					+ ". Your balance is ₹" + account.get().getBalance() + ".";
			sendEmailNotification(account, requestDto, "withdraw", emailMessage, userList);
			throw new WithdrawalLimitReachedException("Your today's withdrawal limit is reached");
		}

		account.get().setBalance(account.get().getBalance() - requestDto.getAmount());
		accountRepository.save(account.get());

		String emailMessage = "You have successfully withdrawn ₹" + requestDto.getAmount() + ". Your new balance is ₹"
				+ account.get().getBalance() + ".";
		sendEmailNotification(account, requestDto, "withdraw", emailMessage, userList);

		return AccountMapper.mapAccountToDto(account.get());
	}

	private void sendEmailNotification(Optional<Account> account, AccountOperationRequestDTO requestDto,
			String notificationType, String emailMessage, Optional<User> userList) {
		String emailSubject = "Transaction alert for your HSBC card";
		emailService.sendTransactionEmail(userList.get().getEmail(), emailSubject, emailMessage);
	}

	public AccountDto deposit(AccountOperationRequestDTO depositRequestDto)
			throws InvalidTokenException, AccountNotFoundException {
		if (!userTokenService.isUserTokenValid(depositRequestDto)) {
			throw new InvalidTokenException("User token is invalid or is expired. Please re-login.");
		}

		// get account from user
		Optional<Account> account = accountRepository.findAccountByUserId(depositRequestDto.getUserId());
		if (account.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}

		Optional<User> userList = userRepository.findByAccountNumber(Long.parseLong(account.get().getAccountNumber()));

		account.get().setBalance(account.get().getBalance() + depositRequestDto.getAmount());
		accountRepository.save(account.get());

		String emailMessage = "You have successfully deposited ₹" + depositRequestDto.getAmount()
				+ ". Your new balance is ₹" + account.get().getBalance() + ".";
		sendEmailNotification(account, depositRequestDto, "withdraw", emailMessage, userList);

		return AccountMapper.mapAccountToDto(account.get());
	}

	public double getBalance(AccountOperationRequestDTO requestDTO)
			throws InvalidTokenException, AccountNotFoundException {
		if (!userTokenService.isUserTokenValid(requestDTO)) {
			throw new InvalidTokenException("User token is invalid or is expired. Please re-login.");
		}

		// get account from user
		Optional<Account> account = accountRepository.findAccountByUserId(requestDTO.getUserId());
		if (account.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}

		if (account.get().getUsers().isEmpty()) {
			throw new RuntimeException("User is not associated with this account");
		}

		return account.get().getBalance();
	}

	public Account save(Account account) {
		return accountRepository.save(account);
	}
}
