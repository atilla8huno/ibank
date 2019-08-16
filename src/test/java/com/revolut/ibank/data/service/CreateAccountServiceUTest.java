package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.mapper.AccountMapper;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite of the class: CreateAccountService")
class CreateAccountServiceUTest {

    private CreateAccountService service;

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @BeforeEach
    void setup() {
        service = new AccountService(repository, mapper);
    }

    @Test
    @DisplayName("Should save personal account for new account with " +
            "account number between 2000 and 10000")
    void givenNameAndBalance_whenSave_thenShouldCreateNewAccount() {
        //given
        mockRepository();

        String name = "AB";
        BigDecimal balance = TEN;
        int minAccountNumber = 2000;
        int maxAccountNumber = 10000;

        //when
        Long accountNumber = service.save(name, balance);

        //then
        assertNotNull(accountNumber);
        assertTrue(accountNumber > minAccountNumber);
        assertTrue(accountNumber < maxAccountNumber);

        verify(repository).save(eq(new AccountEntity(name, accountNumber, balance)));
    }

    @ParameterizedTest
    @MethodSource("invalidParametersToCreateAccount")
    @DisplayName("Should NOT accept invalid paramenters when saving new account")
    void givenInvalidParameters_whenSave_thenShouldThrowException(
            String name,
            BigDecimal balance) {
        //given parameters
        //when
        Executable save = () -> service.save(name, balance);

        //then
        assertThrows(IllegalArgumentException.class, save);
    }

    private void mockRepository() {
        when(repository.save(any()))
                .thenReturn(new AccountEntity("AB", 123L, TEN));
    }

    private static Stream<Arguments> invalidParametersToCreateAccount() {
        return Stream.of(
                arguments("AB", null),
                arguments(null, TEN)
        );
    }
}
