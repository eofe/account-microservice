package com.eofe.accountmicroservice.service;

import com.eofe.accountmicroservice.dto.AccountDTO;
import com.eofe.accountmicroservice.entity.Account;
import com.eofe.accountmicroservice.exception.ResourceNotFoundException;
import com.eofe.accountmicroservice.mapper.AccountMapper;
import com.eofe.accountmicroservice.repository.AccountRepository;
import com.eofe.accountmicroservice.util.ErrorMessage;
import com.eofe.accountmicroservice.util.InfoMessage;
import com.eofe.accountmicroservice.util.Validator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final InfoMessage infoMessage;
    private final ErrorMessage errorMessage;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, InfoMessage infoMessage, ErrorMessage errorMessage) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.infoMessage = infoMessage;
        this.errorMessage = errorMessage;
    }

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (Objects.isNull(accountDTO)) {
            throw new IllegalArgumentException(errorMessage.getNullDto());
        }

        Account account = accountMapper.toEntity(accountDTO);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDTO(savedAccount);
    }

    @Override
    @Transactional
    public AccountDTO getAccount(String accountNumber) {

        Validator.isAccountNumberValid(accountNumber);

        log.info(infoMessage.getFetch());

        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.getNotExist().replace("{}", accountNumber)));
    }

    @Override
    public AccountDTO updateAccount(String accountNumber, AccountDTO accountDTO) {

        Validator.isAccountNumberValid(accountNumber);
        log.info(infoMessage.getUpdate(), accountNumber);

        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> {
                    Optional.ofNullable(accountDTO.getName()).ifPresent(account::setName);
                    Optional.ofNullable(accountDTO.getEmail()).ifPresent(account::setEmail);
                    Optional.ofNullable(accountDTO.getBalance()).ifPresent(account::setBalance);

                    Account updatedAccount = accountRepository.save(account);
                    log.info(infoMessage.getUpdateSuccess(), accountNumber);

                    return accountMapper.toDTO(updatedAccount);
                })
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage.getNotFound().replace("{}", accountNumber)));
    }

    @Override
    public void deleteAccount(String accountNumber) {
        log.info(infoMessage.getDeleteRequest(), accountNumber);
        Validator.isAccountNumberValid(accountNumber);
        accountRepository.findByAccountNumber(accountNumber)
                .ifPresentOrElse(
                        account -> {
                            accountRepository.delete(account);
                            log.info(infoMessage.getDeleteSuccess().replace("{}", accountNumber));
                        },
                        () -> {
                            log.error(errorMessage.getNotFound().replace("{}", accountNumber));
                            throw new ResourceNotFoundException("Account not found for account number: " + accountNumber);
                        }
                );
    }
}
