package com.hackathon.orangepod.atm.model;

import com.hackathon.orangepod.atm.utils.AccountUtils;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="ACCOUNT", schema="public")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ACCOUNTID")
    private long accountId;
    @Column(name="ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name="BALANCE")
    private double balance;
    @Column(name="CARD_NUMBER",nullable = false,unique = true,length = 16)
    private String cardNumber;
    @Column(name="CVV",nullable = false,length = 3)
    private String cvv;
    @Column(name = "ISSUE_DATE",nullable = false)
    private LocalDate issueDate;
    @Column(name = "EXPIRY_DATE",nullable = false)
    private LocalDate expiryDate;


    @ManyToMany(mappedBy = "accounts", cascade = CascadeType.MERGE)
    private List<User> users;
}