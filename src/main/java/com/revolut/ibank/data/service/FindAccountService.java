package com.revolut.ibank.data.service;

import com.revolut.ibank.domain.PersonalAccount;

import java.util.Optional;

public interface FindAccountService {

    Optional<PersonalAccount> findByAccountNumber(Long accountNumber);
}
