package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.Account;
import com.revolut.ibank.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccountService
        implements FindAccountService, CreateAccountService, UpdateBalanceAccountService {

    private static final Random RANDOM = new Random();
    private static final int MIN_ACCOUNT_NUMBER = 2_000;
    private static final int MAX_ACCOUNT_NUMBER = 10_000;

    private AccountRepository repository;
    private AccountMapper mapper;

    @Override
    public Optional<Account> findByAccountNumber(@NonNull Long accountNumber) {
        return Optional
                .ofNullable(repository.findByAccountNumber(accountNumber))
                .map(mapper::mapToPersonalAccount);
    }

    @Override
    @Transactional
    public Long save(@NonNull String name,
                     @NonNull BigDecimal balance) {
        long accountNumber = generateAccountNumber();
        AccountEntity accountEntity = new AccountEntity(name, accountNumber, balance);

        repository.save(accountEntity);

        return accountNumber;
    }

    @Override
    @Transactional
    public void updateBalance(@NonNull Set<Account> accounts) {

        accounts.forEach(account -> {
            AccountEntity entity =
                    repository.findByAccountNumber(account.getAccountNumber());
            entity.setBalance(account.getBalance());

            repository.save(entity);
        });
    }

    private long generateAccountNumber() {
        return RANDOM.nextInt((MAX_ACCOUNT_NUMBER - MIN_ACCOUNT_NUMBER) + 1)
                + MIN_ACCOUNT_NUMBER;
    }
}
