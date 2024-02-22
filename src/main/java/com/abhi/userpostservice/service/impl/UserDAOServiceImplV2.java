package com.abhi.userpostservice.service.impl;

import com.abhi.userpostservice.exception.UserNotFoundException;
import com.abhi.userpostservice.model.User;
import com.abhi.userpostservice.repo.IUserDtlsRepository;
import com.abhi.userpostservice.service.IUserDAOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("userDAOServiceImplV2")
public class UserDAOServiceImplV2 implements IUserDAOService {
    private final IUserDtlsRepository userDtlsRepository;

    @Override
    public List<User> findAll() {
        return userDtlsRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) {
        return userDtlsRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found. Id: " + id));
    }

    @Override
    public User createUser(User user) {
        user.setId(null);
        return userDtlsRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDtlsRepository.findById(id).ifPresentOrElse(userDtlsRepository::delete, () -> {
            throw new UserNotFoundException("User not found. Id: " + id);
        });
    }
}
