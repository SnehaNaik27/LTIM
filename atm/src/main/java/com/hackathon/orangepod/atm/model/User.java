package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_Id")
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



}
