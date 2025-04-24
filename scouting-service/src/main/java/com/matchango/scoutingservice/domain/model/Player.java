package com.matchango.scoutingservice.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    private int age;
    @Enumerated(EnumType.STRING)
    private Position position;
};
