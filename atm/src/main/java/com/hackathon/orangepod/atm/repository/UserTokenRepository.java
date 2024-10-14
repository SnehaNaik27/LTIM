package com.hackathon.orangepod.atm.repository;
import com.hackathon.orangepod.atm.DTO.UserLogoutRequest;
import com.hackathon.orangepod.atm.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserLogoutRequest, Long> {


    Optional<UserToken> findByTokenAndExpiredFalse(String token);

    Optional<UserToken> findByAccountNumberAndToken(String accountNumber, String token);

    void save(UserToken userToken);
}

