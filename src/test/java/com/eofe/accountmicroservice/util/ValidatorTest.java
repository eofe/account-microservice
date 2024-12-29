package com.eofe.accountmicroservice.util;

import com.eofe.accountmicroservice.exception.InvalidAccountNumberException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    public void shouldThrowExceptionWhenAccountNumberIsNull() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Validator.isAccountNumberValid(null))
                .withMessage("Account number is null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenAccountNumberIsEmpty() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Validator.isAccountNumberValid(""))
                .withMessage("Account number is null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenAccountNumberIsBlank() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Validator.isAccountNumberValid("   "))
                .withMessage("Account number is null or empty");
    }

    @Test
    public void shouldThrowInvalidAccountNumberExceptionForInvalidFormat() {

        assertThatExceptionOfType(InvalidAccountNumberException.class)
                .isThrownBy(() -> Validator.isAccountNumberValid("ACC12345XYZ"))
                .withMessage("Account number format is invalid. Expected format: ACC followed by 10 hexadecimal characters.");
    }

    @Test
    public void shouldNotThrowExceptionForValidAccountNumber() {

        String validAccountNumber = "ACC1234567890";
        assertDoesNotThrow(() -> Validator.isAccountNumberValid(validAccountNumber));
    }

    @Test
    public void shouldThrowInvalidAccountNumberExceptionForTooShortAccountNumber() {

        assertThatExceptionOfType(InvalidAccountNumberException.class)
                .isThrownBy(() -> Validator.isAccountNumberValid("ACC12345"))
                .withMessage("Account number format is invalid. Expected format: ACC followed by 10 hexadecimal characters.");
    }

    @Test
    public void shouldThrowInvalidAccountNumberExceptionForTooLongAccountNumber() {

        assertThatExceptionOfType(InvalidAccountNumberException.class)
                .isThrownBy(() -> Validator.isAccountNumberValid("ACC12345678901234"))
                .withMessage("Account number format is invalid. Expected format: ACC followed by 10 hexadecimal characters.");
    }
}