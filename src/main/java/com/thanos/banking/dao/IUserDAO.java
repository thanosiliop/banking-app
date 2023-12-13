package com.thanos.banking.dao;

import com.thanos.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO  extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
