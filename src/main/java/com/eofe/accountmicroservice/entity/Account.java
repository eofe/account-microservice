package com.eofe.accountmicroservice.entity;

import com.eofe.accountmicroservice.util.AccountNumberGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNTS")
public class Account extends BaseEntity{


    @Column(name = "ACCOUNT_NUMBER")
    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;

    @NotBlank
    @Column
    private String name;

    @Email
    @Column
    private String email;

    @Column
    @PositiveOrZero
    private BigDecimal balance;

    public Account() {
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.accountNumber = AccountNumberGenerator.generateAccountNumber();
    }

    public Account(UUID id, String accountNumber, String name, String email, BigDecimal balance) {
        this.setId(id);
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.balance = balance;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
