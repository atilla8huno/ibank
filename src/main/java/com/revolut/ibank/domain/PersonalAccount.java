package com.revolut.ibank.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Getter
@ToString(callSuper = true)
public class PersonalAccount extends Account {

    private final String name;
    private final Long accountNumber;

    public PersonalAccount(@NonNull String name,
                           @NonNull Long accountNumber,
                           @NonNull BigDecimal balance) {
        super(balance);
        this.name = name;
        this.accountNumber = accountNumber;
    }

    @Override
    public void transferFrom(@NonNull Account accountFrom,
                             @NonNull BigDecimal amountToTransfer) {

        if (doesNotHaveEnoughBalance(accountFrom, amountToTransfer)) {
            throw new IllegalStateException("Not enough balance");
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(amountToTransfer));
        setBalance(getBalance().add(amountToTransfer));
    }

    private boolean doesNotHaveEnoughBalance(Account accountFrom,
                                             BigDecimal amountToTransfer) {
        return ZERO.compareTo(accountFrom.getBalance().subtract(amountToTransfer)) > 0;
    }
}
