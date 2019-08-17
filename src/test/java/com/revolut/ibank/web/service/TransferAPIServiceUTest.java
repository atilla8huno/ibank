package com.revolut.ibank.web.service;

import com.revolut.ibank.data.service.FindAccountService;
import com.revolut.ibank.data.service.UpdateBalanceAccountService;
import com.revolut.ibank.domain.Account;
import com.revolut.ibank.domain.PersonalAccount;
import com.revolut.ibank.web.service.request.TransferRequest;
import com.revolut.ibank.web.service.response.TransferResponse;
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
import org.mockito.junit.jupiter.MockitoSettings;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
@DisplayName("Test suite of the class: TransferAPIService")
class TransferAPIServiceUTest {

    private TransferAPIService apiService;

    @Mock
    private FindAccountService findAccountService;

    @Mock
    private UpdateBalanceAccountService updateBalanceAccountService;

    @BeforeEach
    void setup() {
        apiService = new TransferAPIServiceImpl(findAccountService,
                updateBalanceAccountService);
    }

    @Test
    @DisplayName("Should NOT accept invalid request when transferring")
    void givenInvalidRequest_whenTransfer_thenShouldThrowException() {
        //given
        TransferRequest request = null;

        //when
        Executable transfer = () -> apiService.transfer(request);

        //then
        assertThrows(IllegalArgumentException.class, transfer);
    }

    @ParameterizedTest
    @MethodSource("nonExistingAccounts")
    @DisplayName("Should NOT transfer when an account does not exist")
    void givenNonExistingAccount_whenTransfer_thenShouldThrowException(
            Long accountNumberFrom,
            Long accountNumberTo) {

        //given
        when(findAccountService.findByAccountNumber(eq(1L)))
                .thenReturn(empty());
        when(findAccountService.findByAccountNumber(eq(2L)))
                .thenReturn(Optional.of(mock(PersonalAccount.class)));

        TransferRequest request =
                new TransferRequest(accountNumberFrom, accountNumberTo, ONE);

        //when
        Executable transfer = () -> apiService.transfer(request);

        //then
        assertThrows(IllegalStateException.class, transfer);
    }

    private static Stream<Arguments> nonExistingAccounts() {
        return Stream.of(
                arguments(1L, 2L),
                arguments(2L, 1L)
        );
    }

    @Test
    @DisplayName("Should transfer an amount from one account to another for a given request")
    void givenTransferRequest_whenTransfer_thenShouldTransferMoneyAndSave() {
        //given
        BigDecimal amountToTransfer = TEN;

        Long accountNumberFrom = 11111L;
        Account accountFrom = mock(PersonalAccount.class);

        Long accountNumberTo = 22222L;
        Account accountTo = mock(PersonalAccount.class);

        mockAccounts(accountNumberFrom, accountNumberTo, accountFrom, accountTo);

        TransferRequest transferRequest =
                new TransferRequest(accountNumberFrom, accountNumberTo, amountToTransfer);

        //when
        TransferResponse response = apiService.transfer(transferRequest);

        //then
        assertNotNull(response);
        assertEquals("Success", response.getMessage());

        verify(findAccountService).findByAccountNumber(eq(accountNumberFrom));
        verify(findAccountService).findByAccountNumber(eq(accountNumberTo));

        verify(accountTo).transferFrom(eq(accountFrom), eq(amountToTransfer));

        verify(updateBalanceAccountService).updateBalance(eq(accountNumberFrom), any());
        verify(updateBalanceAccountService).updateBalance(eq(accountNumberTo), any());
    }

    private void mockAccounts(Long accountNumberFrom,
                              Long accountNumberTo,
                              Account accountFrom,
                              Account accountTo) {
        when(findAccountService.findByAccountNumber(eq(accountNumberFrom)))
                .thenReturn(Optional.of(accountFrom));
        when(findAccountService.findByAccountNumber(eq(accountNumberTo)))
                .thenReturn(Optional.of(accountTo));

        when(accountFrom.getBalance()).thenReturn(TEN);
        when(accountFrom.getAccountNumber()).thenReturn(accountNumberFrom);
        when(accountTo.getBalance()).thenReturn(TEN);
        when(accountTo.getAccountNumber()).thenReturn(accountNumberTo);
    }
}
