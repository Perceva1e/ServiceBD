package com.example.servicedb.dto;

import lombok.Data;

@Data
public class PersonnelDTO {
    private Long id;
    private String position;
    private PersonDTO person;
}