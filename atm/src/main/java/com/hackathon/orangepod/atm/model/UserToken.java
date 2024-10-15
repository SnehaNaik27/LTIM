package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TOKEN")
@Builder
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(name="token")
    private String token;

    @Column(name="isExpired")
    private boolean isExpired;

   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    public Long getTokenId() {
        return tokenId;
    }

    public String getToken() {
        return token;
    }

    public User getUserId() {
        return userId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public void setUserId(User userId) {
        this.userId = userId;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
