package com.revolut.ibank.web.service;

import com.revolut.ibank.data.service.CreateAccountService;
import com.revolut.ibank.web.request.NewAccountRequest;
import com.revolut.ibank.web.service.response.NewAccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite of the class: NewAccountAPIService")
class NewAccountAPIServiceUTest {

    private NewAccountAPIService apiService;

    @Mock
    private CreateAccountService createAccountService;

    @BeforeEach
    void setup() {
        apiService = new NewAccountAPIServiceImpl(createAccountService);
    }

    @Test
    @DisplayName("Should NOT accept invalid request")
    void givenInvalidRequest_whenCreateNewAccount_thenShouldThrowException() {
        //given
        NewAccountRequest request = null;

        //when
        Executable createNewAccount = () -> apiService.createNewAccount(request);

        //then
        assertThrows(IllegalArgumentException.class, createNewAccount);
    }

    @Test
    @DisplayName("Should create account for a given new account request")
    void givenNewAccountRequest_whenCreateNewAccount_thenShouldCreateAccount() {
        //given
        mockCreateAccountService();

        String clientName = "John Doe";
        BigDecimal initialBalance = TEN;
        NewAccountRequest request = new NewAccountRequest(clientName, initialBalance);

        //when
        NewAccountResponse response = apiService.createNewAccount(request);

        //then
        assertNotNull(response);
        assertNotNull(response.getAccountNumber());
        assertEquals("Success", response.getMessage());

        verify(createAccountService).save(eq(clientName), eq(initialBalance));
    }

    private void mockCreateAccountService() {
        when(createAccountService.save(any(), any())).thenReturn(1234L);
    }
}
