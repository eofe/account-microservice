package com.eofe.accountmicroservice.util;

import java.util.UUID;

public class AccountNumberGenerator {

    public static String generateAccountNumber() {

        return "ACC" + UUID.randomUUID().toString().substring(0, 5).toUpperCase() + String.valueOf(System.currentTimeMillis()).substring(0, 5).toUpperCase();
    }
}
