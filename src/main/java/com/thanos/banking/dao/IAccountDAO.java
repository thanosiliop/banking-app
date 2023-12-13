package com.thanos.banking.dao;

import com.thanos.banking.model.Account;
import com.thanos.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAccountDAO extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}