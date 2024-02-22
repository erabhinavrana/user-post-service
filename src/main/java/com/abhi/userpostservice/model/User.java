package com.abhi.userpostservice.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@JsonFilter("CustomUserDtlsFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class User {
    private Integer id;
    @Size(min = 2, message = "ERROR_USER_NAME")
    private String name;
    @Past(message = "ERROR_USER_BIRTHDATE")
    private LocalDate birthDate;
    @JsonProperty("gender")
    private String genderDtls;
    @JsonIgnore
    @JsonProperty("marital_status")
    private String maritalStatus;
}
