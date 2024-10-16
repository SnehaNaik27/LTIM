package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

    @ManyToMany(mappedBy = "accounts", cascade = CascadeType.MERGE)
    private List<User> users;
}