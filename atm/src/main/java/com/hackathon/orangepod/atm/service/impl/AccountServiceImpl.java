package com.hackathon.orangepod.atm.service.impl;

import java.util.Optional;

import com.hackathon.orangepod.atm.DTO.*;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import java.time.LocalDateTime;


@Service
public class AccountServiceImpl implements AccountService {

	private final String EMAIL_SUBJECT = "Transaction alert for your HSBC card";

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

		User userList = userRepository.findByAccountNumber(Long.parseLong(account.get().getAccountNumber()));

		if (account.get().getBalance() < requestDto.getAmount()) {
			String emailMessage = "Insufficient funds to withdraw ₹" + requestDto.getAmount() + ". Your balance is ₹"
					+ account.get().getBalance() + ".";
			emailService.sendTransactionEmail(userList.getEmail(), EMAIL_SUBJECT, emailMessage);
			throw new InsufficientFundsException("Insufficient funds");

		}

		if (!userTokenService.isWithdrawalLimitValid(requestDto)) {
			String emailMessage = "Your today's withdrawal limit is reached for ₹" + requestDto.getAmount()
					+ ". Your balance is ₹" + account.get().getBalance() + ".";
			emailService.sendTransactionEmail(userList.getEmail(), EMAIL_SUBJECT, emailMessage);
			throw new WithdrawalLimitReachedException("Your today's withdrawal limit is reached");
		}

		account.get().setBalance(account.get().getBalance() - requestDto.getAmount());
		accountRepository.save(account.get());

		String emailMessage = "You have successfully withdrawn ₹" + requestDto.getAmount() + ". Your new balance is ₹"
				+ account.get().getBalance() + ".";
		emailService.sendTransactionEmail(userList.getEmail(), EMAIL_SUBJECT, emailMessage);

		return AccountMapper.mapAccountToDto(account.get());
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

		User userList = userRepository.findByAccountNumber(Long.parseLong(account.get().getAccountNumber()));

		account.get().setBalance(account.get().getBalance() + depositRequestDto.getAmount());
		accountRepository.save(account.get());

		String emailMessage = "You have successfully deposited ₹" + depositRequestDto.getAmount()
				+ ". Your new balance is ₹" + account.get().getBalance() + ".";
		emailService.sendTransactionEmail(userList.getEmail(), EMAIL_SUBJECT, emailMessage);

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


	@Override
	public GetBalanceReceiptResponse generateCurrBalReceipt(Long userId, String token) {

		Optional<Account> account = accountRepository.findAccountByUserId(userId);
		if (account.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}
		String maskAccountNumber = AccountUtils.maskNumber(account.get().getAccountNumber());

		GetBalanceReceiptResponse receipt = GetBalanceReceiptResponse.builder()
				.accountNumber(account.get().getAccountNumber())
				.dateTime(LocalDateTime.now())
				.build();
		receipt.setAccountNumber(maskAccountNumber);
		receipt.setDateTime(LocalDateTime.now());
		receipt.setAvailableBalance(account.get().getBalance());
		return receipt;
	}

	@Override
	public ReceiptResponse generateReceipt(Long userId, String token, Long amount) {

		Optional<Account> account = accountRepository.findAccountByUserId(userId);
		if (account.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}
		String maskAccountNumber = AccountUtils.maskNumber(account.get().getAccountNumber());

		ReceiptResponse receipt = ReceiptResponse.builder()
				.accountNumber(account.get().getAccountNumber())
				.dateTime(LocalDateTime.now())
				.build();
		receipt.setAccountNumber(maskAccountNumber);
		receipt.setDateTime(LocalDateTime.now());
		receipt.setWithdrawalBalance(amount);
		receipt.setAvailableBalance(account.get().getBalance());
		return receipt;
	}


	@Override
	public DepositeReceiptResponse generateDepositReceipt(Long userId, String token, Long amount) {

		Optional<Account> account = accountRepository.findAccountByUserId(userId);
		if (account.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}
		String maskAccountNumber = AccountUtils.maskNumber(account.get().getAccountNumber());

		DepositeReceiptResponse receipt = DepositeReceiptResponse.builder()
				.accountNumber(account.get().getAccountNumber())
				.dateTime(LocalDateTime.now())
				.build();
		receipt.setAccountNumber(maskAccountNumber);
		receipt.setDateTime(LocalDateTime.now());
		receipt.setDepositBalance(amount);
		receipt.setAvailableBalance(account.get().getBalance());
		return receipt;
	}
}
