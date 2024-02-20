package com.abhi.userpostservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@ToString
public class User {
    private Integer id;
    private String name;
    private LocalDate birthDate;
}
