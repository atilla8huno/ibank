package com.revolut.ibank.data.service;

import com.revolut.ibank.domain.Account;

import java.util.Optional;

public interface FindAccountService {

    Optional<Account> findByAccountNumber(Long accountNumber);
}
