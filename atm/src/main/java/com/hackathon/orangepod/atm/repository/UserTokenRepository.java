package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {


    //Optional<UserToken> findByTokenAndIsExpiredFalse(String token);

    @Query("select t from UserToken t where t.isExpired = false And t.userId= :userId")
    Optional<UserToken> findByTokenByUserId(@Param("userId") Long userId);



     //UserToken save(UserToken userToken);
}

