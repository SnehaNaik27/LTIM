package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
