package com.revolut.ibank.data.service;

import com.revolut.ibank.domain.Account;

import java.util.Set;

public interface UpdateBalanceAccountService {

    void updateBalance(Set<Account> accounts);
}
