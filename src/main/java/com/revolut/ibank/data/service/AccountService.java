package com.revolut.ibank.data.service;

import com.revolut.ibank.domain.PersonalAccount;
import com.revolut.ibank.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements FindAccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public Optional<PersonalAccount> findByAccountNumber(@NonNull Long accountNumber) {
        return Optional
                .ofNullable(repository.findByAccountNumber(accountNumber))
                .map(mapper::mapToPersonalAccount);
    }
}
