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

@DisplayName("Test suite of the class: AccountDomainToEntityMapper")
class AccountDomainToEntityMapperUTest {

    private AccountDomainToEntityMapper domainMapper;

    @BeforeEach
    void setup() {
        domainMapper = new AccountMapper();
    }

    @Test
    @DisplayName("Should convert a personal account into account entity")
    void givenPersonalAccount_whenMapToAccountEntity_thenShouldConvert() {
        //given
        String name = "ASB";
        Long accountNumber = 123L;
        BigDecimal balance = TEN;

        PersonalAccount personalAccount =
                new PersonalAccount(name, accountNumber, balance);

        //when
        AccountEntity accountEntity = domainMapper.mapToAccountEntity(personalAccount);

        //then
        assertNotNull(accountEntity);
        assertEquals(name, accountEntity.getName());
        assertEquals(accountNumber, accountEntity.getAccountNumber());
        assertEquals(balance, accountEntity.getBalance());
    }

    @Test
    @DisplayName("Should NOT accept invalid arguments when mapping to account entity")
    void givenInvalidPersonalAccount_whenMapToAccountEntity_thenShouldThrowException() {
        //given
        PersonalAccount personalAccount = null;

        //when
        Executable mapToAccountEntity =
                () -> domainMapper.mapToAccountEntity(personalAccount);

        //then
        assertThrows(IllegalArgumentException.class, mapToAccountEntity);
    }
}
