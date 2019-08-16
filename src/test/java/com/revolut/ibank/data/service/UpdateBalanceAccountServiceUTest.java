package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    @DisplayName("Should update balance of an account")
    void givenAccountNumberAndBalance_whenUpdateBalance_thenShouldUpdateBalance() {
        //given
        mockRepository();

        Long accountNumber = 123123L;
        BigDecimal balance = TEN;

        //when
        service.updateBalance(accountNumber, balance);

        //then
        verify(repository).findByAccountNumber(eq(accountNumber));
        verify(repository).save(any());
    }

    @ParameterizedTest
    @MethodSource("invalidParametersToUpdateBalance")
    @DisplayName("Should NOT accept invalid parameters when updating balance")
    void givenInvalidParameters_whenUpdateBalance_thenShouldThrowException(
            Long accountNumber,
            BigDecimal balance) {
        //given parameters
        //when
        Executable save = () -> service.updateBalance(accountNumber, balance);

        //then
        assertThrows(IllegalArgumentException.class, save);
    }

    private static Stream<Arguments> invalidParametersToUpdateBalance() {
        return Stream.of(
                arguments(123L, null),
                arguments(null, TEN)
        );
    }

    private void mockRepository() {
        AccountEntity dummyAccount = new AccountEntity("AB", 123L, TEN);

        when(repository.findByAccountNumber(any()))
                .thenReturn(dummyAccount);
        when(repository.save(any()))
                .thenReturn(dummyAccount);
    }
}
