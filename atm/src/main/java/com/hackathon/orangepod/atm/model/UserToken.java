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
    @Column(name = "TOKENID")
    private Long tokenId;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "ISEXPIRED")
    private boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

}
