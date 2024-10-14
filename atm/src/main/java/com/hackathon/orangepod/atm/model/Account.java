package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Account")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AccountId;
    private long AccountNumber;
    private BigDecimal Balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
