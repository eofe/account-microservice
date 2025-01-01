package com.eofe.accountmicroservice.config;

import com.eofe.accountmicroservice.util.ErrorMessage;
import com.eofe.accountmicroservice.util.Validator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public AppConfig(ErrorMessage errorMessage) {
        Validator.setErrorMessage(errorMessage);
    }
}
