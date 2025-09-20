package com.example.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;

    // Get all accounts
    @GetMapping
    public List<Account> getAll() {
        return accountRepo.findAll();
    }

    // Get account by ID
    @GetMapping("/{id}")
    public Optional<Account> getById(@PathVariable Long id) {
        return accountRepo.findById(id);
    }

    // Create a new account
    @PostMapping
    public Account create(@RequestBody Account acc) {
        if (acc.getBalance() == null) {
            acc.setBalance(BigDecimal.ZERO);
        }
        return accountRepo.save(acc);
    }

    // Update an account
    @PutMapping("/{id}")
    public Account update(@PathVariable Long id, @RequestBody Account acc) {
        return accountRepo.findById(id).map(existing -> {
            existing.setOwner(acc.getOwner());
            existing.setAccountNumber(acc.getAccountNumber());
            existing.setBalance(acc.getBalance());
            return accountRepo.save(existing);
        }).orElseGet(() -> {
            acc.setId(id);
            return accountRepo.save(acc);
        });
    }

    // Delete account
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        accountRepo.deleteById(id);
    }
}
