package com.abhi.userpostservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@ToString
public class User {
    private Integer id;
    @Size(min = 2, message = "Sorry, the user name must be at least 2 characters long.")
    private String name;
    @Past(message = "Sorry, the user birth date must be in the past.")
    private LocalDate birthDate;
}
