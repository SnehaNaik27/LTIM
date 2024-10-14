package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByContact(long contact);
}
