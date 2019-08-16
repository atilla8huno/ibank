package com.revolut.ibank.mapper;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.PersonalAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test suite of the class: AccountEntityToDomainMapper")
class AccountEntityToDomainMapperUTest {

    private AccountEntityToDomainMapper entityMapper;

    @BeforeEach
    void setup() {
        entityMapper = new AccountMapper();
    }

    @Test
    @DisplayName("Should NOT accept invalid arguments when mapping to personal account")
    void givenInvalidAccountEntity_whenMapToPersonalAccount_thenShouldThrowException() {
        //given
        AccountEntity accountEntity = null;

        //when
        Executable mapToPersonalAccount =
                () -> entityMapper.mapToPersonalAccount(accountEntity);

        //then
        assertThrows(IllegalArgumentException.class, mapToPersonalAccount);
    }

    @Test
    @DisplayName("Should convert an account entity into personal account")
    void givenAccountEntity_whenMapToPersonalAccount_thenShouldConvert() {
        //given
        String name = "ASB";
        Long accountNumber = 123L;
        BigDecimal balance = TEN;
        AccountEntity accountEntity = new AccountEntity(name, accountNumber, balance);

        //when
        PersonalAccount personalAccount = entityMapper.mapToPersonalAccount(accountEntity);

        //then
        assertNotNull(personalAccount);
        assertEquals(name, personalAccount.getName());
        assertEquals(accountNumber, personalAccount.getAccountNumber());
        assertEquals(balance, personalAccount.getBalance());
    }
}
