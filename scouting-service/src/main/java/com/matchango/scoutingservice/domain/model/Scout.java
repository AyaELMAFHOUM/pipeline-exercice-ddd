package com.matchango.scoutingservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Scout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
}

