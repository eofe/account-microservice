package com.eofe.accountmicroservice.service;

import com.eofe.accountmicroservice.dto.AccountDTO;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccount(String accountNumber);
}
