package com.revolut.ibank.mapper;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.PersonalAccount;

public interface AccountDomainToEntityMapper {

    AccountEntity mapToAccountEntity(PersonalAccount personalAccount);
}
