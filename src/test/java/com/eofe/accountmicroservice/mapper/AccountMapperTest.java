package com.eofe.accountmicroservice.mapper;

import com.eofe.accountmicroservice.dto.AccountDTO;
import com.eofe.accountmicroservice.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void toDTO_shouldMapAccountToAccountDTO() {
        var id = UUID.randomUUID();
        var accountId = "ACC0C49817350";

        Account account = new Account(id, accountId, "John Doe", "john.doe@example.com", BigDecimal.valueOf(1000));
        AccountDTO accountDTO = accountMapper.toDTO(account);

        assertThat(accountDTO).isNotNull();
        assertThat(accountDTO.getId()).isEqualTo(id);
        assertThat(accountDTO.getAccountNumber()).isEqualTo(accountId);
        assertThat(accountDTO.getName()).isEqualTo("John Doe");
        assertThat(accountDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(accountDTO.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void toDTO_shouldThrowExceptionWhenAccountIsNull() {
        assertThatThrownBy(() -> accountMapper.toDTO(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Account entity cannot be null");
    }

    @Test
    void toEntity_shouldMapAccountDTOToAccount() {
        var id = UUID.randomUUID();
        var accountId = "ACC0C49817350";

        AccountDTO accountDTO = new AccountDTO(id, accountId, "John Doe", "john.doe@example.com", BigDecimal.valueOf(1000));
        Account account = accountMapper.toEntity(accountDTO);

        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo(id);
        assertThat(account.getAccountNumber()).isEqualTo(accountId);
        assertThat(account.getName()).isEqualTo("John Doe");
        assertThat(account.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void toEntity_shouldThrowExceptionWhenAccountDTOIsNull() {
        assertThatThrownBy(() -> accountMapper.toEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("AccountDTO cannot be null");
    }

    @Test
    void toDTOSet_shouldMapSetOfAccountsToSetOfAccountDTOs() {
        var id = UUID.randomUUID();
        var secondId = UUID.randomUUID();
        var accountId = "ACC0C49817350";
        var secondAccountId = "ACC0C49817357";

        Account account1 = new Account(id, accountId, "John Doe", "john.doe@example.com", BigDecimal.valueOf(1000));
        Account account2 = new Account(secondId, secondAccountId, "Jane Doe", "jane.doe@example.com", BigDecimal.valueOf(2000));
        Set<Account> accounts = Set.of(account1, account2);

        Set<AccountDTO> accountDTOs = accountMapper.toDTOSet(accounts);

        assertThat(accountDTOs).hasSize(2);
        assertThat(accountDTOs).extracting(AccountDTO::getAccountNumber)
                .containsExactlyInAnyOrder(accountId, secondAccountId);
    }

    @Test
    void toDTOSet_shouldReturnEmptySetWhenInputSetIsEmpty() {
        Set<AccountDTO> accountDTOs = accountMapper.toDTOSet(Set.of());
        assertThat(accountDTOs).isEmpty();
    }

    @Test
    void toDTOSet_shouldThrowExceptionWhenInputSetIsNull() {
        assertThatThrownBy(() -> accountMapper.toDTOSet(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accounts set cannot be null");
    }

    @Test
    void toEntitySet_shouldMapSetOfAccountDTOsToSetOfAccounts() {
        AccountDTO accountDTO1 = new AccountDTO(UUID.randomUUID(), "ACC1234567890", "John Doe", "john.doe@example.com", BigDecimal.valueOf(1000));
        AccountDTO accountDTO2 = new AccountDTO(UUID.randomUUID(), "ACC1234567891", "Jane Doe", "jane.doe@example.com", BigDecimal.valueOf(2000));
        Set<AccountDTO> accountDTOs = Set.of(accountDTO1, accountDTO2);

        Set<Account> accounts = accountMapper.toEntitySet(accountDTOs);

        assertThat(accounts).hasSize(2);
        assertThat(accounts).extracting(Account::getAccountNumber)
                .containsExactlyInAnyOrder("ACC1234567890", "ACC1234567891");
    }

    @Test
    void toEntitySet_shouldReturnEmptySetWhenInputSetIsEmpty() {
        Set<Account> accounts = accountMapper.toEntitySet(Set.of());
        assertThat(accounts).isEmpty();
    }

    @Test
    void toEntitySet_shouldThrowExceptionWhenInputSetIsNull() {
        assertThatThrownBy(() -> accountMapper.toEntitySet(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("AccountDTOs set cannot be null");
    }
}