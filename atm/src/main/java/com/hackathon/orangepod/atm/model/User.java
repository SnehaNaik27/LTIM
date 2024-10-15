package com.hackathon.orangepod.atm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
}
