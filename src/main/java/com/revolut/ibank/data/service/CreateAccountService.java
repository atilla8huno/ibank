package com.revolut.ibank.data.service;

import java.math.BigDecimal;

public interface CreateAccountService {

    Long save(String name, BigDecimal balance);
}
