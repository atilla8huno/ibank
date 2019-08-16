package com.revolut.ibank.mapper;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.PersonalAccount;

public interface AccountEntityToDomainMapper {

    PersonalAccount mapToPersonalAccount(AccountEntity accountEntity);
}
