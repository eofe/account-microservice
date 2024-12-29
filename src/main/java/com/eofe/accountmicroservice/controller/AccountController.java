package com.eofe.accountmicroservice.controller;

import com.eofe.accountmicroservice.dto.AccountDTO;
import com.eofe.accountmicroservice.service.AccountService;
import com.eofe.accountmicroservice.util.Validator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        logger.info("Received request to create account with name: {}", accountDTO.getName());
        var createdAccount = accountService.createAccount(accountDTO);
        logger.info("Created account with ID: {}", createdAccount.getId());
        return ResponseEntity.created(URI.create("/api/v1/accounts/" + createdAccount.getId()))
                .body(createdAccount);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("accountNumber") String accountNumber) {
        logger.info("Received request to get account with account number: {}", accountNumber);
        Validator.isAccountNumberValid(accountNumber);
        var account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(account);
    }
}
