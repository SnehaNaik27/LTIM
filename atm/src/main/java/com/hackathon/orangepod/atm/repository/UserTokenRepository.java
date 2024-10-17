package com.hackathon.orangepod.atm.repository;

import com.hackathon.orangepod.atm.model.User;
import com.hackathon.orangepod.atm.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    @Query("select t from UserToken t join t.user u " +
            "where t.isExpired = false And u.userId= :userId order by t.tokenId desc limit 1")
    Optional<UserToken> findTokenByUserId(@Param("userId") Long userId);

    @Query("select t from UserToken t where t.isExpired = false And t.token= :token")
    Optional<UserToken> findByToken(@Param("token") String token);

    @Query("SELECT ut FROM UserToken ut join ut.user u " +
            "WHERE u.userId = :userId AND ut.withdrawalDate = :withdrawalDate " +
            "order by ut.tokenId desc limit 1"
    )
    UserToken findByUserAndWithdrawalDate(@Param("userId") Long userId, @Param("withdrawalDate") LocalDate withdrawalDate);
}
