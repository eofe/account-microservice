package com.eofe.accountmicroservice.util;

import com.eofe.accountmicroservice.exception.InvalidAccountNumberException;
import org.apache.logging.log4j.util.Strings;
import java.util.regex.Pattern;

public class Validator {

    private static final String ACCOUNT_NUMBER_REGEX = "^ACC[0-9A-F]{10}$";
    private static final Pattern PATTERN = Pattern.compile(ACCOUNT_NUMBER_REGEX);

    public static void isAccountNumberValid(String accountNumber) {
        if (Strings.isBlank(accountNumber) || Strings.isEmpty(accountNumber)) {
            throw new IllegalArgumentException("Account number is null or empty");
        }

        if (!PATTERN.matcher(accountNumber).matches()) {
            throw new InvalidAccountNumberException(
                    "Account number format is invalid. Expected format: ACC followed by 10 hexadecimal characters."
            );
        }
    }
}
