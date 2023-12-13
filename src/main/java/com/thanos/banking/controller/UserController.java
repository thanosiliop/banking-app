package com.thanos.banking.controller;

import com.thanos.banking.model.Account;
import com.thanos.banking.model.User;
import com.thanos.banking.service.AccountService;
import com.thanos.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        if (newUser != null) {
            return "redirect:/success";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User loginUser = userService.getUserByUsername(user.getUsername());
        if (loginUser != null && loginUser.getPassword().equals(user.getPassword())) {
            return "redirect:/success";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/accounts/{username}")
    public String accountsPage(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);
        List<Account> userAccounts = accountService.getUserAccounts(user);
        model.addAttribute("userAccounts", userAccounts);
        return "accounts";
    }
}