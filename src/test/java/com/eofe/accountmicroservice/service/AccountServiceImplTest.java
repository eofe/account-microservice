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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private Validator validator;

    @Mock
    private InfoMessage infoMessage;

    @Mock
    private ErrorMessage errorMessage;

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
        // Arrange
        try (var mockStatic = mockStatic(Validator.class)) {
            // Mock the static method call
            mockStatic.when(() -> Validator.isAccountNumberValid(anyString()))
                    .thenThrow(new IllegalArgumentException("Account number is null or empty"));

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> accountService.getAccount(""));
        }
    }

    @Test
    void getAccount_shouldThrowEntityNotFoundException_whenAccountDoesNotExist() {

        String accountNumber = "ACC0CC6F17353";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());
        when(errorMessage.getNotExist()).thenReturn("Account with account number " + accountNumber + " does not exist.");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountService.getAccount(accountNumber));
        assertThat(exception.getMessage()).isEqualTo("Account with account number " + accountNumber + " does not exist.");
    }


    @Test
    void shouldUpdateAccountSuccessfully() {

        String accountNumber = "ACC1234567890";
        AccountDTO inputDTO = new AccountDTO("Updated Name", "updatedEmail@hotmail.com", new BigDecimal(500));
        var id = UUID.randomUUID();
        Account existingAccount = new Account(id, "ACC1234567890","Original Name", "originalEmail@hotmail.com", new BigDecimal(300));
        Account updatedAccount = new Account(id, "ACC1234567890","Updated Name", "updatedEmail@hotmail.com", new BigDecimal(500));
        AccountDTO expectedDTO = new AccountDTO("Updated Name", "updatedEmail@hotmail.com", new BigDecimal(500));

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(updatedAccount);
        when(accountMapper.toDTO(updatedAccount)).thenReturn(expectedDTO);

        AccountDTO result = accountService.updateAccount(accountNumber, inputDTO);

        assertThat(result).isEqualTo(expectedDTO);
        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountRepository).save(existingAccount);
        verify(accountMapper).toDTO(updatedAccount);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {

        String accountNumber = "ACC1234567890";
        AccountDTO inputDTO = new AccountDTO("Updated Name", "updatedEmail@hotmail.com", new BigDecimal(500));

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());
        when(errorMessage.getNotFound()).thenReturn("Account not found for account number: " + accountNumber);

        assertThatThrownBy(() -> accountService.updateAccount(accountNumber, inputDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Account not found for account number: " + accountNumber);

        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).toDTO(any());
    }

    @Test
    void shouldNotUpdateFieldsWhenNullInDTO() {

        String accountNumber = "ACC1234567890";
        var id = UUID.randomUUID();
        AccountDTO inputDTO = new AccountDTO(null, null, null); // No updates provided
        Account existingAccount = new Account(id, accountNumber,"Original Name", "originalEmail@hotmail.com", new BigDecimal(300));
        AccountDTO expectedDTO = new AccountDTO(id, accountNumber, "Original Name", "originalEmail@hotmail.com", new BigDecimal(300));

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(existingAccount); // No changes
        when(accountMapper.toDTO(existingAccount)).thenReturn(expectedDTO);

        AccountDTO result = accountService.updateAccount(accountNumber, inputDTO);

        assertThat(result).isEqualTo(expectedDTO);
        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountRepository).save(existingAccount);
        verify(accountMapper).toDTO(existingAccount);
    }

    @Test
    void shouldDeleteAccountSuccessfully() {

        String accountNumber = "ACC1234567890";
        Account account = new Account(UUID.randomUUID(), accountNumber, "James Brown", "james@brown.com", new BigDecimal(500));

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(infoMessage.getDeleteSuccess()).thenReturn("Successfully deleted account with account number: ACC1234567890");

        accountService.deleteAccount(accountNumber);

        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountRepository).delete(account);
    }

    @Test
    void shouldThrowExceptionWhenAccountToBeDeletedNotFound() {

        String accountNumber = "ACC1234567111";

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());
        when(errorMessage.getNotFound()).thenReturn("Account not found for account number: " + accountNumber);

        assertThatThrownBy(() -> accountService.deleteAccount(accountNumber))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Account not found for account number: " + accountNumber);

        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountRepository, never()).delete(any());
    }
}
