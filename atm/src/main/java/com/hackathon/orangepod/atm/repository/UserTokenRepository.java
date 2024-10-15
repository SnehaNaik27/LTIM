package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

   UserToken findByAccountNumber(String accountNumber);

}
