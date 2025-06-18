package com.example.servicedb.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String biography;
    private String photograph;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Personnel personnel;
}