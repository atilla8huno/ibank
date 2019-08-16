package com.revolut.ibank.mapper;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.PersonalAccount;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountMapperImpl implements AccountMapper {

    public PersonalAccount mapToPersonalAccount(@NonNull AccountEntity accountEntity) {
        return new PersonalAccount(
                accountEntity.getName(),
                accountEntity.getAccountNumber(),
                accountEntity.getBalance());
    }
}
