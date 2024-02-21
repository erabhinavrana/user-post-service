package com.abhi.userpostservice.controller;

import com.abhi.userpostservice.model.User;
import com.abhi.userpostservice.service.IUserDAOService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/social/v1")
public class UserController {

    private final IUserDAOService userDAOServiceImpl;

    @GetMapping("/users")
    public List<User> findAll() {
        return userDAOServiceImpl.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findUserById(@PathVariable Integer id) {
        return EntityModel.of(userDAOServiceImpl.findUserById(id))
                .add(WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findAll()).withRel("all-users"));
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
