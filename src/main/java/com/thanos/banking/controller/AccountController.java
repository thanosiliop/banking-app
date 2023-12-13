package com.thanos.banking.controller;

import com.thanos.banking.model.Account;
import com.thanos.banking.model.User;
import com.thanos.banking.service.AccountService;
import com.thanos.banking.service.TransferRequest;
import com.thanos.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @GetMapping("/deposit/{accountId}")
    public String depositPage(@PathVariable Long accountId, Model model) {
        Account account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "deposit";
    }

    @GetMapping("/withdraw/{accountId}")
    public String withdrawPage(@PathVariable Long accountId, Model model) {
        Account account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "withdraw";
    }


    @GetMapping("/transfer/{accountId}")
    public String transferPage(@PathVariable Long accountId, Model model) {
        Account account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "transfer";
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            List<Account> userAccounts = accountService.getUserAccounts(user);
            return new ResponseEntity<>(userAccounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Account> deposit(@ModelAttribute TransactionForm depositForm) {
        Long accountId = depositForm.getAccountId();
        double amount = depositForm.getAmount();

        Account account = accountService.deposit(accountId, amount);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Account> withdraw(@ModelAttribute TransactionForm withdrawForm) {
        Long accountId = withdrawForm.getAccountId();
        double amount = withdrawForm.getAmount();

        Account account = accountService.withdraw(accountId, amount);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@ModelAttribute TransferForm transferForm) {
        long sourceAccountId = transferForm.getSourceAccountId();
        long destinationAccountId = transferForm.getDestinationAccountId();
        double amount = transferForm.getAmount();

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccountId(sourceAccountId);
        transferRequest.setDestinationAccountId(destinationAccountId);
        transferRequest.setAmount(amount);

        boolean success = accountService.transfer(transferRequest);

        if (success) {
            return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Transfer failed", HttpStatus.BAD_REQUEST);
        }
    }
}