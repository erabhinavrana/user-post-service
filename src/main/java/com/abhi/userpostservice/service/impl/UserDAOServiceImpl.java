package com.abhi.userpostservice.service.impl;

import com.abhi.userpostservice.exception.UserNotFoundException;
import com.abhi.userpostservice.model.User;
import com.abhi.userpostservice.service.IUserDAOService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class UserDAOServiceImpl implements IUserDAOService {

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(11, "Ahmed", LocalDate.now().minusYears(25), "Male", "Married"));
        users.add(new User(22, "Alia", LocalDate.now().minusYears(30), "Female", "Single"));
        users.add(new User(33, "Sofia", LocalDate.now().minusYears(35), "Female", "Married"));
    }

    private static Predicate<User> getUserPredicate(Integer id) {
        return u -> u.getId().equals(id);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findUserById(Integer id) {
        return users.stream().filter(getUserPredicate(id)).findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found. Id: " + id));
    }

    @Override
    public User createUser(User user) {
        user.setId((users.size() + 1) * 11);
        users.add(user);
        return user;
    }

    @Override
    public void deleteUserById(Integer id) {
        users.removeIf(getUserPredicate(id));
    }
}
