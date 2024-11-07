package com.hackathon.orangepod.atm.service.impl;


import com.hackathon.orangepod.atm.DTO.*;
import com.hackathon.orangepod.atm.mapper.AccountMapper;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.AccountService;
import com.hackathon.orangepod.atm.service.OtpService;
import com.hackathon.orangepod.atm.service.UserService;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountServices;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private EmailService emailService;

    public ATMResponse createUser(UserDto userDTO) {
        Account account = new Account();
        account.setAccountNumber(AccountUtils.generateAccountNumber());
        account.setCardNumber(AccountUtils.generateCardNumber());
        account.setCvv(AccountUtils.generateCVV());
        LocalDate issueDate= LocalDate.now();
        account.setIssueDate(AccountUtils.calculateExpiryDate(issueDate));
        LocalDate expiryDate = issueDate.plusYears(5);
        account.setExpiryDate(AccountUtils.calculateExpiryDate(expiryDate));
        account.setBalance(0);

        // Create new User
        User newUser = User.builder()
                .name(userDTO.getName())
                .address(userDTO.getAddress())
                .contact(userDTO.getContact())
                .pin(userDTO.getPin())
                .build();

        // Save User and Accounts
        Account savedAccount = accountServices.save(account);
        newUser.setAccounts(List.of(savedAccount));
        userRepository.save(newUser);

        String emailSubject = "New User alert for your HSBC account";
        String emailMessage = "Welcome to HSBC ATM services. Your account number is: " + account.getAccountNumber();
        emailService.sendTransactionEmail(newUser.getEmail(), emailSubject, emailMessage);

        // Prepare Response
        return ATMResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountDto(AccountMapper.mapAccountToDto(account)) // You can set account details here if needed
                .build();
    }

    public UserLoginResponse login(UserLoginRequest request) {
        //Business logic to validate account and pin
        Optional<User> user = userRepository.findUserByAccountAndPin(request.getAccountNumber(), request.getPin());

        if (user.isEmpty()) {
            return null;
        }

        LocalDate currentDate = LocalDate.now(); //currentdate

        //fetch the token for the user for current date (if exists)
        UserToken existingToken = userTokenRepository.findByUserAndWithdrawalDate(user.get().getUserId(), currentDate);
        UserToken userToken = new UserToken();

        //Create token on successful login
        String token = UUID.randomUUID().toString();

        userToken.setUser(user.get());
        userToken.setToken(token);
        userToken.setExpired(false);
        userToken.setWithdrawalDate(LocalDate.now()); // set the date of today


        if (existingToken != null) {
            //token exists for today, copy the withdrawal limit
            if (!existingToken.isExpired()) {
                existingToken.setExpired(true);
                userTokenRepository.save(existingToken);
            }
            userToken.setWithdrawalLimit(existingToken.getWithdrawalLimit());
        } else {
            //set default withdrawal limit of 20000 if no token exists
            userToken.setWithdrawalLimit(20000.00);
        }

        userToken.setWithdrawalDate(LocalDate.now());
        userTokenRepository.save(userToken);

        return UserLoginResponse.builder()
                .token(token)
                .userId(user.get().getUserId())
                .message("Login successful")
                .build();
    }

    public String checkPin(UserLoginRequest request) {
        User user = userRepository.findByAccountNumber(request.getAccountNumber());

        LocalDateTime now = LocalDateTime.now();

        if (user.getLockedUntil() != null && now.isBefore(user.getLockedUntil())) {
            return "Account is locked. Try again later.";
        }

        if (!user.getPin().equals(request.getPin())) {
            user.setAttempts(user.getAttempts() + 1);
            if (user.getAttempts() >= 3) {
                user.setLockedUntil(now.plusMinutes(1));
                user.setAttempts(0);
                userRepository.save(user);
                return "Too many failed attempts. Account locked for 1 minute.";
            }
            userRepository.save(user);
            return "Incorrect PIN. Attempt " + user.getAttempts();
        }

        user.setAttempts(0);
        userRepository.save(user);
        return "PIN is correct. Login successful!";
    }

    public String logout(Long userId){
        // Check if the token exists for the given account and is not expired
        Optional<UserToken> userTokenOpt = userTokenRepository.findTokenByUserId(userId);
        if (userTokenOpt.isPresent()) {
            UserToken userToken = userTokenOpt.get();
            // Mark the token as expired
            userToken.setExpired(true);
            userTokenRepository.save(userToken);
            return "Logout successful";
        } else {
            return "Invalid token";
        }
    }

    public boolean validateLogin(UserLoginRequest request) {
        //Business logic to validate account and pin
        Optional<User> user = userRepository.findUserByAccountAndPin(request.getAccountNumber(), request.getPin());
        return user.isPresent();
    }

    public boolean validateContactNumber(UserDto request) {
        /// Check if user already exists by contact number
        return userRepository.existsByContact(request.getContact());
    }

    @Override
    public int generateAndSendOtp(long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        int otp = otpService.generateOtp();
        User user = optionalUser.get();
        String emailSubject = "OTP alert for your HSBC account";
        String emailMessage = "Your 6 digit OTP is: " + otp + " Note: Never share your OTP with anyone. " +
                "Bank never asks for OTP.";
        emailService.sendTransactionEmail(user.getEmail(), emailSubject, emailMessage);
        user.setOtp(otp);
        userRepository.save(user);
        return otp; //real time its send to user
    }

    @Override
    public void updatePin(UpdatePinDto updatePinDto) throws IllegalArgumentException {
        //Retrieve user by id
        Optional<User> optionalUser = userRepository.findById(updatePinDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = optionalUser.get();
        //validate the otp
        if (!otpService.validateOtp(user.getOtp(), updatePinDto.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        //update the user pin
        user.setPin(updatePinDto.getNewPin());
        userRepository.save(user);
    }
}
