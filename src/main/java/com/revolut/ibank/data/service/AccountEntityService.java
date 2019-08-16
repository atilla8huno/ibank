package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountEntityService implements FindAccountService {

    private AccountRepository repository;

    @Override
    public Optional<AccountEntity> findByAccountNumber(@NonNull Long accountNumber) {
        return Optional.ofNullable(repository.findByAccountNumber(accountNumber));
    }
}
