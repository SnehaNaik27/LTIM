package com.hackathon.orangepod.atm.service;


import com.hackathon.orangepod.atm.Dto.ATMResponse;
import com.hackathon.orangepod.atm.Dto.AccountDto;
import com.hackathon.orangepod.atm.Dto.UserDto;
import com.hackathon.orangepod.atm.model.Account;
import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import com.hackathon.orangepod.atm.repository.UserRepository;
import com.hackathon.orangepod.atm.repository.UserTokenRepository;
import com.hackathon.orangepod.atm.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackathon.orangepod.atm.Dto.UserLogoutRequestDTO;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public ATMResponse createAccount(UserDto userDTO) {
        // Check if user already exists by contact number
        if (userRepository.existsByContact(userDTO.getContact())) {
            return ATMResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountDtos(null)
                    .build();
        }

        // Create new User
        User newUser = User.builder()
                .name(userDTO.getName())
                .address(userDTO.getAddress())
                .contact(userDTO.getContact())
                .pin(userDTO.getPin())
                .build();


        // Create Accounts
        List<Account> accounts = userDTO.getAccounts().stream().map(accountDTO -> {
            Account account = new Account();
            account.setAccountNumber(AccountUtils.generateAccountNumber());
            account.setBalance(0);;
            account.setUser(newUser);
            return account;
        }).collect(Collectors.toList());

        newUser.setAccounts(accounts);

        // Save User and Accounts
        User savedUser = userRepository.save(newUser);

        // Map Account entities to AccountDto
        List<AccountDto> accountDtos = savedUser.getAccounts().stream().map(account ->
                AccountDto.builder()
                        .accountNumber(account.getAccountNumber())
                        .balance(0.0)
                        .build()
        ).collect(Collectors.toList());

        // Prepare Response
        return ATMResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountDtos(accountDtos) // You can set account details here if needed
                .build();
    }

    public com.hackathon.orangepod.atm.dto.UserLoginResponse login(com.hackathon.orangepod.atm.dto.UserLoginRequest request) {
        //Business logic to validate account and pin

        String token = UUID.randomUUID().toString();

        UserToken userToken = new UserToken();
        userToken.setAccountNumber(request.getAccountNumber());
        userToken.setToken(token);
        userTokenRepository.save(userToken);

        com.hackathon.orangepod.atm.dto.UserLoginResponse response = new com.hackathon.orangepod.atm.dto.UserLoginResponse();
        response.setToken(token);
        response.setMessage("Login successful");

        return response;
    }
    
//@Autowire
    public void logout( String token){
//        boolean istokenInvalid=true;
//        if(istokenInvalid){
//            return "Logout successful";
//        }else {
//            return "Logout failed: Invalid token";
//        }


    }
}
