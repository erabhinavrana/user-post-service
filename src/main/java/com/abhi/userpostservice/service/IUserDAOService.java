package com.abhi.userpostservice.service;

import com.abhi.userpostservice.model.User;

import java.util.List;

public interface IUserDAOService {
    List<User> findAll();

    User findUserById(Integer id);

    User createUser(User user);

    void deleteUserById(Integer id);
}
