package com.revolut.ibank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@AllArgsConstructor
public abstract class Account {

    @Getter
    @Setter
    private BigDecimal balance;

    public abstract void transferFrom(Account accountFrom,
                                      BigDecimal amountToTransfer);
}
