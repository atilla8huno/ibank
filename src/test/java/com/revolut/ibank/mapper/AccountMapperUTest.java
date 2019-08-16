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

@DisplayName("Test suite of the class: AccountMapper")
class AccountMapperUTest {

    private AccountMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new AccountMapperImpl();
    }

    @Test
    @DisplayName("Should NOT accept invalid arguments when mapping to personal account")
    void givenInvalidAccountEntity_whenMaptoPersonalAccount_thenShouldThrowException() {
        //given
        AccountEntity accountEntity = null;

        //when
        Executable mapToPersonalAccount =
                () -> mapper.mapToPersonalAccount(accountEntity);

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
        PersonalAccount personalAccount = mapper.mapToPersonalAccount(accountEntity);

        //then
        assertNotNull(personalAccount);
        assertEquals(name, personalAccount.getName());
        assertEquals(accountNumber, personalAccount.getAccountNumber());
        assertEquals(balance, personalAccount.getBalance());
    }
}
