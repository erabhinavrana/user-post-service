package com.abhi.userpostservice.repo;

import com.abhi.userpostservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDtlsRepository extends JpaRepository<User, Integer> {
}
