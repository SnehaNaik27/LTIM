package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long userId;
       private String name;
       private String address;
       private Long contact;
       private Long pin;

       @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
       private List<Account> accounts;
}
