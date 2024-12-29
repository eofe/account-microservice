package com.eofe.accountmicroservice.service;

import com.eofe.accountmicroservice.dto.AccountDTO;
import com.eofe.accountmicroservice.entity.Account;
import com.eofe.accountmicroservice.mapper.AccountMapper;
import com.eofe.accountmicroservice.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountDTO accountDTO;
    private AccountDTO accountDTO_1;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        accountDTO = new AccountDTO("John Doe", "john.doe@example.com", new BigDecimal(1000));
        accountDTO_1 = new AccountDTO(UUID.randomUUID(), "ACC0CC6F17353","John Doe", "john.doe@example.com", new BigDecimal(1000));


        account = new Account();
        account.setId(UUID.randomUUID());
        account.setAccountNumber("ACC0CC6F17353");
        account.setVersion(0);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setName("John Doe");
        account.setEmail("john.doe@example.com");
        account.setBalance(new BigDecimal(1000));
    }

    @Test
    void createAccount_shouldReturnAccountDTO_whenValidAccountDTOIsProvided() {
        when(accountMapper.toEntity(accountDTO)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDTO(account)).thenReturn(accountDTO);

        AccountDTO result = accountService.createAccount(accountDTO);

        assertThat(result).isNotNull();
        assertThat(result.getAccountNumber()).isEqualTo(accountDTO.getAccountNumber());
        assertThat(result.getName()).isEqualTo(accountDTO.getName());
        assertThat(result.getEmail()).isEqualTo(accountDTO.getEmail());
        assertThat(result.getBalance()).isEqualByComparingTo(accountDTO.getBalance());

        verify(accountMapper).toEntity(accountDTO);
        verify(accountRepository).save(account);
        verify(accountMapper).toDTO(account);
    }

    @Test
    void createAccount_shouldThrowIllegalArgumentException_whenAccountDTOIsNull() {

        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(null));
    }

    @Test
    void getAccount_shouldReturnAccountDTO_whenAccountExists() {

        String accountNumber = "ACC0CC6F17353";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountMapper.toDTO(account)).thenReturn(accountDTO_1);

        AccountDTO result = accountService.getAccount(accountNumber);

        assertThat(result).isNotNull();
        assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
        assertThat(result.getName()).isEqualTo(accountDTO.getName());
        assertThat(result.getEmail()).isEqualTo(accountDTO.getEmail());
        assertThat(result.getBalance()).isEqualByComparingTo(accountDTO.getBalance());

        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountMapper).toDTO(account);
    }

    @Test
    void getAccount_shouldThrowIllegalArgumentException_whenAccountNumberIsBlank() {

        assertThrows(IllegalArgumentException.class, () -> accountService.getAccount(""));
    }

    @Test
    void getAccount_shouldThrowEntityNotFoundException_whenAccountDoesNotExist() {

        String accountNumber = "ACC0CC6F17353";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountService.getAccount(accountNumber));
        assertThat(exception.getMessage()).isEqualTo("Account with account number " + accountNumber + " does not exist.");
    }
}
