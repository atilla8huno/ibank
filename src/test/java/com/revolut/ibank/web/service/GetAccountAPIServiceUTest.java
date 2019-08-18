package com.revolut.ibank.web.service;

import com.revolut.ibank.data.service.FindAccountService;
import com.revolut.ibank.domain.PersonalAccount;
import com.revolut.ibank.web.service.request.GetAccountRequest;
import com.revolut.ibank.web.service.response.GetAccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.math.BigDecimal.TEN;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite of the class: GetAccountAPIService")
class GetAccountAPIServiceUTest {

    private GetAccountAPIService apiService;

    @Mock
    private FindAccountService findAccountService;

    @BeforeEach
    void setup() {
        apiService = new GetAccountAPIServiceImpl(findAccountService);
    }

    @Test
    @DisplayName("Should NOT accept invalid request")
    void givenInvalidRequest_whenGetAccount_thenShouldThrowException() {
        //given
        GetAccountRequest request = null;

        //when
        Executable createNewAccount = () -> apiService.getAccount(request);

        //then
        assertThrows(IllegalArgumentException.class, createNewAccount);
    }

    @Test
    @DisplayName("Should get account for a given request")
    void givenGetAccountRequest_whenGetAccount_thenShouldGetAccount() {
        //given
        Long accountNumber = 123L;
        mockGetAccountService(accountNumber);

        GetAccountRequest request = new GetAccountRequest(accountNumber);

        //when
        GetAccountResponse response = apiService.getAccount(request);

        //then
        assertNotNull(response);
        assertNotNull(response.getName());
        assertNotNull(response.getAccountNumber());
        assertNotNull(response.getBalance());
        assertEquals(accountNumber, response.getAccountNumber());

        assertEquals("Success", response.getMessage());

        verify(findAccountService).findByAccountNumber(eq(accountNumber));
    }

    @Test
    @DisplayName("Should throw error if account does NOT exist")
    void givenNonExistingAccountNumber_whenGetAccount_thenShouldThrowError() {
        //given
        Long accountNumber = 321L;
        when(findAccountService.findByAccountNumber(eq(accountNumber)))
                .thenReturn(empty());

        GetAccountRequest request = new GetAccountRequest(accountNumber);

        //when
        Executable getAccount = () -> apiService.getAccount(request);

        //then
        assertThrows(IllegalStateException.class, getAccount);

        verify(findAccountService).findByAccountNumber(eq(accountNumber));
    }

    private void mockGetAccountService(Long accountNumber) {
        when(findAccountService.findByAccountNumber(eq(accountNumber)))
                .thenReturn(of(new PersonalAccount("AB", accountNumber, TEN)));
    }
}
