package com.thanos.banking.service;

import com.thanos.banking.dao.IAccountDAO;
import com.thanos.banking.model.Account;
import com.thanos.banking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private IAccountDAO accountRepository;

    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUser(user);
    }

    public Account deposit(Long accountId, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setBalance(account.getBalance() + amount);
            return accountRepository.save(account);
        } else {
            return null;
        }
    }

    public Account withdraw(Long accountId, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                return accountRepository.save(account);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean transfer(TransferRequest transferRequest) {
        Account source = accountRepository.findById(transferRequest.getSourceAccountId()).orElse(null);
        Account destination = accountRepository.findById(transferRequest.getDestinationAccountId()).orElse(null);

        if (source != null && destination != null && source.getBalance() >= transferRequest.getAmount()) {
            source.setBalance(source.getBalance() - transferRequest.getAmount());
            destination.setBalance(destination.getBalance() + transferRequest.getAmount());

            accountRepository.save(source);
            accountRepository.save(destination);

            return true;
        } else {
            return false;
        }
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
}