package com.eofe.accountmicroservice.util;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AccountNumberGeneratorTest {

    @Test
    public void shouldGenerateUniqueAccountNumber() {
        String accountNumber1 = generateAccountNumber();
        String accountNumber2 = generateAccountNumber();

        assertThat(accountNumber1).isNotEqualTo(accountNumber2);
    }

    @Test
    public void shouldGenerateAccountNumberWithCorrectPrefix() {
        String accountNumber = generateAccountNumber();

        assertThat(accountNumber).startsWith("ACC");
    }

    @Test
    public void shouldGenerateAccountNumberWithCorrectLength() {
        String accountNumber = generateAccountNumber();

        assertThat(accountNumber).hasSize(13);
    }

    public static String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().substring(0, 5).toUpperCase() + String.valueOf(System.currentTimeMillis()).substring(0, 5).toUpperCase();
    }
}