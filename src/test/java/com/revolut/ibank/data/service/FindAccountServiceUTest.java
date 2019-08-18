package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.domain.Account;
import com.revolut.ibank.domain.PersonalAccount;
import com.revolut.ibank.mapper.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite of the class: FindAccountService")
class FindAccountServiceUTest {

    private FindAccountService service;

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @BeforeEach
    void setup() {
        service = new AccountService(repository, mapper);
    }

    @Test
    @DisplayName("Should NOT accept invalid account number when finding by account number")
    void givenInvalidAccountNumber_whenFindByAccountNumber_thenShouldThrowException() {
        //given
        Long accountNumber = null;

        //when
        Executable findByAccountNumber =
                () -> service.findByAccountNumber(accountNumber);

        //then
        assertThrows(IllegalArgumentException.class, findByAccountNumber);
    }

    @Test
    @DisplayName("Should find account by account number")
    void givenAccountNumber_whenFindByAccountNumber_thenShouldFindEntity() {
        //given
        Long accountNumber = 123123L;
        mockRepository(accountNumber);

        //when
        Optional<Account> personalAccount
                = service.findByAccountNumber(accountNumber);

        //then
        assertNotNull(personalAccount);
        assertTrue(personalAccount.isPresent());
        assertEquals(accountNumber, personalAccount.get().getAccountNumber());

        verify(repository).findByAccountNumber(eq(accountNumber));
        verify(mapper).mapToPersonalAccount(any());
    }

    private void mockRepository(Long accountNumber) {
        when(repository.findByAccountNumber(eq(accountNumber)))
                .thenReturn(new AccountEntity("AB", accountNumber, TEN));
        when(mapper.mapToPersonalAccount(any()))
                .thenReturn(new PersonalAccount("AB", accountNumber, TEN));
    }
}
