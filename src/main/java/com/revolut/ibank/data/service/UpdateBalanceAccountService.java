package com.revolut.ibank.data.service;

import java.math.BigDecimal;

public interface UpdateBalanceAccountService {

    void updateBalance(Long accountNumber, BigDecimal balance);
}
