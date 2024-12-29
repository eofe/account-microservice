package com.eofe.accountmicroservice.service;

import com.eofe.accountmicroservice.dto.AccountDTO;
import com.eofe.accountmicroservice.entity.Account;
import com.eofe.accountmicroservice.mapper.AccountMapper;
import com.eofe.accountmicroservice.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (Objects.isNull(accountDTO)) {
            throw new IllegalArgumentException("AccountDTO cannot be null");
        }


        Account account = accountMapper.toEntity(accountDTO);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDTO(savedAccount);
    }

    @Override
    @Transactional
    public AccountDTO getAccount(String accountNumber) {
        if (StringUtils.isBlank(accountNumber)) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }

        log.info("Fetching account with account number: {}", accountNumber);

        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Account with account number " + accountNumber + " does not exist."));
    }
}
