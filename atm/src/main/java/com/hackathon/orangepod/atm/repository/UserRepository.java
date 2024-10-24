package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByContact(long contact);

    @Query("select u from User u " +
            " join u.accounts a where a.accountNumber = :accountNumber And u.pin= :pin")
    Optional<User> findUserByAccountAndPin(@Param("accountNumber") Long accountNumber, @Param("pin") Long pin);

    @Query("SELECT u FROM User u JOIN u.accounts a WHERE a.accountNumber = :accountNumber")
    User findByAccountNumber(@Param("accountNumber") Long accountNumber);
}
