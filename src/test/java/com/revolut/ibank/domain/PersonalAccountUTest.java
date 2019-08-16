package com.revolut.ibank.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Test suite of the class: PersonalAccount")
class PersonalAccountUTest {

    @Test
    @DisplayName("Should instantiate new account with parameters")
    void givenParameters_whenNew_thenShouldCreateAccount() {
        //given
        String name = "Atilla Barros";
        Long accountNumber = 123123L;
        BigDecimal balance = TEN;

        //when
        PersonalAccount account = new PersonalAccount(name, accountNumber, balance);

        //then
        assertNotNull(account);
        assertEquals(name, account.getName());
        assertEquals(accountNumber, account.getAccountNumber());
        assertEquals(balance, account.getBalance());
    }

    @Test
    @DisplayName("Should transfer amount from one account to another")
    void givenAccountAndAmount_whenTransferFrom_thenShouldTransferFromOneAccountToAnother() {
        //given
        BigDecimal initialBalance = TEN;
        BigDecimal amountToTransfer = ONE;
        Account accountFrom = dummyAccount(initialBalance);
        Account accountTo = dummyAccount(initialBalance);

        //when
        accountTo.transferFrom(accountFrom, amountToTransfer);

        //then
        assertEquals(initialBalance.add(amountToTransfer), accountTo.getBalance());
        assertEquals(initialBalance.subtract(amountToTransfer), accountFrom.getBalance());
    }

    @Test
    @DisplayName("Should NOT transfer from one account without enough balance")
    void givenAccountWithoutEnoughBalance_whenTransferFrom_thenShouldThrowException() {
        //given
        BigDecimal amountToTransfer = TEN;
        Account accountFrom = dummyAccount(ONE);
        Account accountTo = dummyAccount(TEN);

        //when
        Executable transferFrom =
                () -> accountTo.transferFrom(accountFrom, amountToTransfer);

        //then
        assertThrows(IllegalStateException.class, transferFrom);
    }

    @ParameterizedTest
    @MethodSource("invalidParametersToTransferFrom")
    @DisplayName("Shoult NOT accept invalid parameters when transferring")
    void givenInvalidParameters_whenTransferFrom_thenShouldThrowException(
            PersonalAccount account,
            BigDecimal amountToTransfer) {
        //given parameters
        //when
        Executable transferFrom =
                () -> dummyAccount(TEN).transferFrom(account, amountToTransfer);

        //then
        assertThrows(IllegalArgumentException.class, transferFrom);
    }

    @ParameterizedTest
    @MethodSource("invalidParametersToCreateAccount")
    @DisplayName("Should NOT accept invalid paramenters when creating new account")
    void givenInvalidParameters_whenNew_thenShouldThrowException(
            String name,
            Long accountNumber,
            BigDecimal balance) {
        //given parameters
        //when
        Executable newAccount =
                () -> new PersonalAccount(name, accountNumber, balance);

        //then
        assertThrows(IllegalArgumentException.class, newAccount);
    }

    @Test
    @DisplayName("Should display properties on toString")
    void givenAccount_whenToString_thenShouldShowValues() {
        //given
        String name = "Atilla Barros";
        Long accountNumber = 123123L;
        BigDecimal balance = TEN;
        PersonalAccount account = new PersonalAccount(name, accountNumber, balance);

        //when
        String valueOfAccount = account.toString();

        //then
        assertTrue(valueOfAccount.contains(name));
        assertTrue(valueOfAccount.contains(accountNumber.toString()));
        assertTrue(valueOfAccount.contains(balance.toString()));
    }

    private Account dummyAccount(BigDecimal initialBalance) {
        return new PersonalAccount("Atilla Barros", 123123L, initialBalance);
    }

    private static Stream<Arguments> invalidParametersToTransferFrom() {
        return Stream.of(
                arguments(new PersonalAccount("", 0L, ZERO), null),
                arguments(null, TEN)
        );
    }

    private static Stream<Arguments> invalidParametersToCreateAccount() {
        return Stream.of(
                arguments("John Doe", 123L, null),
                arguments("John Doe", null, TEN),
                arguments(null, 123L, TEN)
        );
    }
}
