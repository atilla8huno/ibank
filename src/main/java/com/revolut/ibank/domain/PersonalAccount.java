package com.revolut.ibank.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PersonalAccount extends Account {

    private final String name;

    public PersonalAccount(@NonNull String name,
                           @NonNull Long accountNumber,
                           @NonNull BigDecimal balance) {
        super(balance, accountNumber);
        this.name = name;
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
