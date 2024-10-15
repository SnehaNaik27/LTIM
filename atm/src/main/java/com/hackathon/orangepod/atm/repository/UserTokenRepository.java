package com.hackathon.orangepod.atm.repository;
import com.hackathon.orangepod.atm.DTO.UserLogoutRequest;
import com.hackathon.orangepod.atm.model.UserToken;
import org.aspectj.weaver.ast.And;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {


    Optional<UserToken> findByTokenAndExpiredFalse(String token);
@Query("select user from UserToken user where user.userId=:userId And user.expired=false")
    Optional<UserToken> findByTokenByUserId(@Param("userId") Long userId);

//   void save(UserToken userToken);
}

