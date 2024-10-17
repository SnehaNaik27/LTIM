package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("select a from Account a " +
            " join a.users u where u.userId = :userId")
    Optional<Account> findAccountByUserId(@Param("userId") Long userId);

}
