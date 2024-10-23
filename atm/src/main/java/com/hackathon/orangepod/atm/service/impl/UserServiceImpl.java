package com.hackathon.orangepod.atm.service.impl;


import com.hackathon.orangepod.atm.DTO.*;
import com.hackathon.orangepod.atm.mapper.AccountMapper;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.service.AccountService;
import com.hackathon.orangepod.atm.service.UserService;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    private UserTokenRepository userTokenRepository;

    @Autowired
    private OtpService otpService;


    public ATMResponse createUser(UserDto userDTO) {
        // Check if user already exists by contact number
        if (userRepository.existsByContact(userDTO.getContact())) {
            return ATMResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountDto(null)
                    .build();
        }

        Account account = new Account();
        account.setAccountNumber(AccountUtils.generateAccountNumber());
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
        User savedUser = userRepository.save(newUser);

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
            return UserLoginResponse.builder()
                    .message("Invalid login credentials")
                    .build();
        }
        //Create token on successful login
        String token = UUID.randomUUID().toString();

        UserToken userToken = new UserToken();
        userToken.setUser(user.get());
        userToken.setToken(token);
        userTokenRepository.save(userToken);

        return UserLoginResponse.builder()
                .token(token)
                .userId(user.get().getUserId())
                .message("Login successful")
                .build();
    }

    public String logout(Long userId) {
        // Check if the token exists for the given account and is not expired
        Optional<UserToken> userTokenOpt = userTokenRepository.findTokenByUserId(userId);
        if (userTokenOpt.isPresent()) {
            UserToken userToken = userTokenOpt.get();
            // Mark the token as expired
            userToken.setExpired(true);
            userTokenRepository.save(userToken);
            return "Logout successful";
        } else {
            return "Invalid token or user ID";
        }

    }

    @Override
    public String generateAndSendOtp(long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        String otp = otpService.generateOtp();
        //send an otp to user(contact or email)
        //As of now for simplicity we are storing in user model

        User user = optionalUser.get();
        user.setPin(Long.valueOf(otp));
        userRepository.save(user);
        return otp; //real time its send to user
    }

    @Override
    public Void updatePin(UpdatePinDto updatePinDto) throws IllegalArgumentException {
        //Retrive user by id
        Optional<User> optionalUser = userRepository.findById(updatePinDto.getUserId());
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = optionalUser.get();
        //validate the otp
        if (!otpService.validateOtp(user.getOtp(), updatePinDto.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        //update the user pin
        user.setPin(updatePinDto.getNewPin());
        user.setOtp(null);
        userRepository.save(user);
        return null;
    }
}
