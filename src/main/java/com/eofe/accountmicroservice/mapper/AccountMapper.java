package com.eofe.accountmicroservice.mapper;

import com.eofe.accountmicroservice.dto.AccountDTO;
import com.eofe.accountmicroservice.entity.Account;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account account) {
        if (Objects.isNull(account)) {
            throw new IllegalArgumentException("Account entity cannot be null");
        }
        return new AccountDTO(account.getId(),
                account.getAccountNumber(),
                account.getName(),
                account.getEmail(),
                account.getBalance()
        );
    }

    public Account toEntity(AccountDTO accountDTO) {
        if (Objects.isNull(accountDTO)) {
            throw new IllegalArgumentException("AccountDTO cannot be null");
        }
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setName(accountDTO.getName());
        account.setBalance(accountDTO.getBalance());
        account.setEmail(accountDTO.getEmail());
        return account;
    }

    public Set<AccountDTO> toDTOSet(Set<Account> accounts) {
        if (Objects.isNull(accounts)) {
            throw new IllegalArgumentException("Accounts set cannot be null");
        }
        if (accounts.isEmpty()) {
            return Set.of();
        }
        return accounts.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<Account> toEntitySet(Set<AccountDTO> accountDTOs) {
        if (Objects.isNull(accountDTOs)) {
            throw new IllegalArgumentException("AccountDTOs set cannot be null");
        }
        if (accountDTOs.isEmpty()) {
            return Set.of();
        }
        return accountDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}