package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.Account;
import com.revolut.ibank.domain.PersonalAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static java.math.BigDecimal.TEN;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite of the class: UpdateBalanceAccountService")
class UpdateBalanceAccountServiceUTest {

    private UpdateBalanceAccountService service;

    @Mock
    private AccountRepository repository;

    @BeforeEach
    void setup() {
        service = new AccountService(repository, null);
    }

    @Test
    @DisplayName("Should updata balance of an account")
    void givenListOfAccounts_whenUpdateBalance_thenShouldUpdateBalance() {
        //given
        mockRepository();

        String name = "JD";
        Long accountNumber = 123123L;
        BigDecimal balance = TEN;

        Set<Account> accounts = new HashSet<>(asList(
                new PersonalAccount(name, accountNumber, balance),
                new PersonalAccount(name, accountNumber + 1, balance)));

        //when
        service.updateBalance(accounts);

        //then
        verify(repository).findByAccountNumber(eq(accountNumber));
        verify(repository, times(accounts.size())).save(any());
    }

    @Test
    @DisplayName("Should NOT accept invalid parameters when updating balance")
    void givenInvalidParameter_whenUpdateBalance_thenShouldThrowException() {
        //given
        Set<Account> accounts = null;

        //when
        Executable save = () -> service.updateBalance(accounts);

        //then
        assertThrows(IllegalArgumentException.class, save);
    }

    private void mockRepository() {
        AccountEntity dummyAccount = new AccountEntity("AB", 123L, TEN);

        when(repository.findByAccountNumber(any()))
                .thenReturn(dummyAccount);
        when(repository.save(any()))
                .thenReturn(dummyAccount);
    }
}
