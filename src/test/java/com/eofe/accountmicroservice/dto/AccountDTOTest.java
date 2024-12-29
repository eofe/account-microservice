package com.eofe.accountmicroservice.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.util.ResourceUtils;
import java.io.IOException;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class AccountDTOTest {

    @Autowired
    private JacksonTester<AccountDTO> json;

    @Test
    void shouldSerializePOSTransaction() throws IOException {
        AccountDTO acc = new AccountDTO("Jane Doe", "jane.doe@gmail.com", new BigDecimal(300));

        var jsonFile = ResourceUtils.getFile("classpath:json/account.json");
        assertThat(json.write(acc)).isEqualToJson(jsonFile);

        assertThat(json.write(acc)).hasJsonPathStringValue("@.name");
        assertThat(json.write(acc)).extractingJsonPathStringValue("@.name").isEqualTo("Jane Doe");

        assertThat(json.write(acc)).hasJsonPathStringValue("@.email");
        assertThat(json.write(acc)).extractingJsonPathStringValue("@.email").isEqualTo("jane.doe@gmail.com");

        assertThat(json.write(acc)).hasJsonPathNumberValue("@.balance");
        assertThat(json.write(acc)).extractingJsonPathNumberValue("@.balance").isEqualTo(300);
    }

    @Test
    void shouldDeserializeAccountDTO() throws IOException {

        String jsonContent = "{\"name\":\"Jane Doe\",\"email\":\"jane.doe@gmail.com\",\"balance\":300}";
        AccountDTO acc = new AccountDTO("Jane Doe", "jane.doe@gmail.com", new BigDecimal(300));

        assertThat(json.parseObject(jsonContent).getName()).isEqualTo("Jane Doe");
        assertThat(json.parseObject(jsonContent).getEmail()).isEqualTo("jane.doe@gmail.com");
        assertThat(json.parseObject(jsonContent).getBalance()).isEqualTo(new BigDecimal(300));
    }
}