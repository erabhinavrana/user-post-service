package com.abhi.userpostservice.controller;

import com.abhi.userpostservice.model.User;
import com.abhi.userpostservice.service.IUserDAOService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/social/v1")
public class UserController {

    private final IUserDAOService userDAOServiceImpl;

    @GetMapping("/users")
    public MappingJacksonValue findAll() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userDAOServiceImpl.findAll());
        PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "birthDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("CustomUserDtlsFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue findUserById(@PathVariable Integer id) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(EntityModel.of(userDAOServiceImpl.findUserById(id))
                .add(WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findAll()).withRel("all-users")));
        PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "birthDate", "gender", "marital_status");
        FilterProvider filters = new SimpleFilterProvider().addFilter("CustomUserDtlsFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User userResult = userDAOServiceImpl.createUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userResult.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userDAOServiceImpl.deleteUserById(id);
    }
}
