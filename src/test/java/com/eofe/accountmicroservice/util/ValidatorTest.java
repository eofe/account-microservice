package com.eofe.accountmicroservice.util;

import com.eofe.accountmicroservice.exception.InvalidAccountNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private ErrorMessage errorMessage;

    @BeforeEach
    void setUp() {
        errorMessage = Mockito.mock(ErrorMessage.class);
        Validator.setErrorMessage(errorMessage);
    }

    @Test
    void testIsAccountNumberValid_ShouldThrowException_WhenAccountNumberIsBlank() {
        // Arrange
        String blankAccountNumber = " ";

        // Mocking error messages
        Mockito.when(errorMessage.getNullOrEmpty()).thenReturn("Account number cannot be blank.");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.isAccountNumberValid(blankAccountNumber);
        });

        assertEquals("Account number cannot be blank.", exception.getMessage());
    }

    @Test
    void testIsAccountNumberValid_ShouldThrowException_WhenAccountNumberIsEmpty() {
        // Arrange
        String emptyAccountNumber = "";

        // Mocking error messages
        Mockito.when(errorMessage.getNullOrEmpty()).thenReturn("Account number cannot be empty.");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.isAccountNumberValid(emptyAccountNumber);
        });

        assertEquals("Account number cannot be empty.", exception.getMessage());
    }

    @Test
    void testIsAccountNumberValid_ShouldThrowException_WhenAccountNumberHasInvalidFormat() {
        // Arrange
        String invalidAccountNumber = "ACC12345";

        // Mocking error messages
        Mockito.when(errorMessage.getBadAccountNumberFormat()).thenReturn("Account number format is invalid.");

        // Act & Assert
        InvalidAccountNumberException exception = assertThrows(InvalidAccountNumberException.class, () -> {
            Validator.isAccountNumberValid(invalidAccountNumber);
        });

        assertEquals("Account number format is invalid.", exception.getMessage());
    }

    @Test
    void testIsAccountNumberValid_ShouldNotThrowException_WhenAccountNumberIsValid() {
        // Arrange
        String validAccountNumber = "ACC123456789A";

        // Mocking error messages (not needed in this case, but necessary for other tests)
        Mockito.when(errorMessage.getNullOrEmpty()).thenReturn("Account number cannot be blank.");
        Mockito.when(errorMessage.getBadAccountNumberFormat()).thenReturn("Account number format is invalid.");

        // Act & Assert
        assertDoesNotThrow(() -> Validator.isAccountNumberValid(validAccountNumber));
    }
}
