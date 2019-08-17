package com.revolut.ibank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public abstract class Account {

    @Setter
    private BigDecimal balance;

    private final Long accountNumber;

    public abstract void transferFrom(Account accountFrom,
                                      BigDecimal amountToTransfer);
}
