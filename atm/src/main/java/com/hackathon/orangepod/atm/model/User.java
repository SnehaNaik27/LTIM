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
@Table(name = "USER")
public class User {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "USER_ID")
        private Long userId;

        @Column(name = "NAME")
        private String name;

        @Column(name = "ADDRESS")
        private String address;

        @Column(name = "PIN")
        private String pin;

        @Column(name="CONTACT")
        private String contact;

        @OneToMany(mappedBy = "userId",cascade = CascadeType.ALL,orphanRemoval = true)
        private List<UserToken> token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

}
