package com.eofe.accountmicroservice.dto;

import com.eofe.accountmicroservice.util.AccountNumberGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AccountDTO {

    @Schema(description = "Unique identifier of the account")
    private UUID id;

    private String accountNumber;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotNull
    @PositiveOrZero
    private BigDecimal balance;

    public AccountDTO() {
        this.accountNumber = AccountNumberGenerator.generateAccountNumber();
    }

    public AccountDTO(UUID id, String accountNumber, String name, String email, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public AccountDTO(String name, String email, BigDecimal balance) {
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, name, email, balance);
    }
}
