package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;

import java.util.Optional;

public interface FindAccountService {

    Optional<AccountEntity> findByAccountNumber(Long accountNumber);
}
