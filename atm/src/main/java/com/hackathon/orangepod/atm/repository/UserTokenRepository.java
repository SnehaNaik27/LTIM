package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    @Query("select t from UserToken t join t.user u " +
            "where t.isExpired = false And u.userId= :userId")
    Optional<UserToken> findTokenByUserId(@Param("userId") Long userId);

    @Query("select t from UserToken t where t.isExpired = false And t.token= :token")
    Optional<UserToken> findByToken(@Param("token") String token);
}
