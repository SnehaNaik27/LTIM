package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import org.springframework.boot.autoconfigure.web.WebProperties;


import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "USER", schema="public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private Long userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PIN")
    private Long pin;

    @Column(name="CONTACT")
    private Long contact;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserToken> token;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="user_account",
    joinColumns = @JoinColumn(name="userId"), inverseJoinColumns = @JoinColumn(name="accountId"))
    private List<Account> accounts;

}
