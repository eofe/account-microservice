package com.eofe.accountmicroservice.repository;

import com.eofe.accountmicroservice.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Sql("/data.sql")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldSaveAccount() {
        Account account = new Account();
        account.setName("test");
        account.setEmail("test@test.com");
        account.setBalance(BigDecimal.valueOf(100));

        var result = accountRepository.save(account);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("test");
        assertThat(result.getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBalance()).isEqualTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldReturnAccountWhenExists() {

        var accountNumber = "ACC0CC6F17353";

        Optional<Account> result = accountRepository.findByAccountNumber(accountNumber);

        assertThat(result).isPresent();
        assertThat(result.get().getAccountNumber()).isEqualTo(accountNumber);
        assertThat(result.get().getName()).isEqualTo("John Doe");
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get().getBalance()).isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void shouldReturnEmptyWhenAccountDoesNotExist() {
        Optional<Account> result = accountRepository.findByAccountNumber("NON_EXISTENT_ACCOUNT");

        assertThat(result).isEmpty();
    }
}