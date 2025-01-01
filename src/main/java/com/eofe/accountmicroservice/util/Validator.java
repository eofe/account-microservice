package com.eofe.accountmicroservice.util;

import com.eofe.accountmicroservice.exception.InvalidAccountNumberException;
import org.apache.logging.log4j.util.Strings;
import java.util.regex.Pattern;

public class Validator {

    private static final String ACCOUNT_NUMBER_REGEX = "^ACC[0-9A-F]{10}$";
    private static final Pattern PATTERN = Pattern.compile(ACCOUNT_NUMBER_REGEX);

    private static ErrorMessage ERROR_MESSAGE;

    public static void setErrorMessage(ErrorMessage errorMessage) {
        ERROR_MESSAGE = errorMessage;
    }

    public static void isAccountNumberValid(String accountNumber) {
        if (Strings.isBlank(accountNumber) || Strings.isEmpty(accountNumber)) {
            throw new IllegalArgumentException(ERROR_MESSAGE.getNullOrEmpty());
        }

        if (!PATTERN.matcher(accountNumber).matches()) {
            throw new InvalidAccountNumberException(ERROR_MESSAGE.getBadAccountNumberFormat());
        }
    }
}
