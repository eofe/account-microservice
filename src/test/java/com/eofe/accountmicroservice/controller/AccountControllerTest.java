package com.eofe.accountmicroservice.controller;

import com.eofe.accountmicroservice.dto.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    private final static String BASE_URL = "/api/v1/accounts";

    @Test
    void shouldCreateAccountSuccessfully() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        AccountDTO accountDTO = new AccountDTO("John Doe", "john.doe@example.com" ,new BigDecimal("100.00"));
        HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO, headers);

        ResponseEntity<AccountDTO> response = restTemplate.postForEntity(BASE_URL, request, AccountDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAccountNumber()).isNotNull();
        assertThat(response.getBody().getAccountNumber()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(accountDTO.getName());
        assertThat(response.getBody().getEmail()).isEqualTo(accountDTO.getEmail());
        assertThat(response.getBody().getBalance()).isEqualByComparingTo(accountDTO.getBalance());
     }

    @Test
    void shouldReturnBadRequestForInvalidEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        AccountDTO invalidAccount = new AccountDTO("John Doe", "invalid-email", new BigDecimal("100.00"));
        HttpEntity<AccountDTO> request = new HttpEntity<>(invalidAccount, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("\"status\":400");
        assertThat(response.getBody()).contains("\"message\":\"email must be a well-formed email address\"");
        assertThat(response.getBody()).contains("\"timestamp\":");
    }

    @Test
    void shouldReturnBadRequestForBlankEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        AccountDTO blankEmailAccount = new AccountDTO("John Doe", "", new BigDecimal("100.00"));
        HttpEntity<AccountDTO> request = new HttpEntity<>(blankEmailAccount, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("\"status\":400");
        assertThat(response.getBody()).contains("\"message\":\"email must not be blank\"");
        assertThat(response.getBody()).contains("\"timestamp\":");
    }

    @Test
    void shouldReturnBadRequestForNullBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        AccountDTO nullBalanceAccount = new AccountDTO("John Doe", "john.doe@example.com", null);
        HttpEntity<AccountDTO> request = new HttpEntity<>(nullBalanceAccount, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("\"status\":400");
        assertThat(response.getBody()).contains("\"message\":\"balance must not be null\"");
        assertThat(response.getBody()).contains("\"timestamp\":");
    }

    @Test
    void shouldReturnBadRequestForNegativeBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        AccountDTO negativeBalanceAccount = new AccountDTO("John Doe", "john.doe@example.com", new BigDecimal("-1.00"));
        HttpEntity<AccountDTO> request = new HttpEntity<>(negativeBalanceAccount, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("\"status\":400");
        assertThat(response.getBody()).contains("\"message\":\"balance must be greater than or equal to 0\"");
        assertThat(response.getBody()).contains("\"timestamp\":");
    }

    @Test
    void shouldReturnFieldSpecificValidationErrors() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        AccountDTO invalidAccount = new AccountDTO(null, null, null);
        HttpEntity<AccountDTO> request = new HttpEntity<>(invalidAccount, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String[] expectedMessages = {
                "email must not be null",
                "name must not be null",
                "email must not be blank",
                "name must not be blank",
                "balance must not be null"
        };

        for (String message : expectedMessages) {
            assertThat(response.getBody()).contains(message);
        }

        assertThat(response.getBody()).contains("\"status\":400", "\"timestamp\":");
    }

    @Test
    void shouldGetAccountByAccountNumberSuccessfully() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + "ACC0CC6F17353", HttpMethod.GET, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody()).contains("\"id\":\"0e6caa84-3e5c-4b4a-8026-86a1aeb7ef89\"");
        assertThat(response.getBody()).contains("\"accountNumber\":\"ACC0CC6F17353\"");
        assertThat(response.getBody()).contains("\"email\":\"john.doe@example.com\"");
        assertThat(response.getBody()).contains("\"balance\":1000.00");
    }

    @Test
    void shouldReturnNotFoundWhenAccountNumberDoesNotExist() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/" + "ACC0000000000", // Non-existent account number
                HttpMethod.GET,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody()).contains("\"status\":404");
        assertThat(response.getBody()).contains("\"message\":\"Account with account number ACC0000000000 does not exist.\"");
        assertThat(response.getBody()).contains("\"timestamp\":");
    }
}