package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TOKEN", schema="public")
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

    @Column(name="WITHDRAWAL_LIMIT")
    private double withdrawalLimit;

    @Column(name="WITHDRAWAL_DATE")
    private LocalDate withdrawalDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
