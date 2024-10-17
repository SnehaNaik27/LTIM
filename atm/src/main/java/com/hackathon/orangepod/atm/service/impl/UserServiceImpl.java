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


import java.time.LocalDate;
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

    public ATMResponse createUser(UserDto userDTO) {
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
}
