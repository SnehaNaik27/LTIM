package com.hackathon.orangepod.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.orangepod.atm.model.Account;

@Repository
public interface DepositRepository extends JpaRepository<Account, Long> {

}
