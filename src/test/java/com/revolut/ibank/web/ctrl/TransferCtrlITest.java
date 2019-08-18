package com.revolut.ibank.web.ctrl;

import com.revolut.ibank.data.entity.AccountEntity;
import com.revolut.ibank.data.service.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Test suite of the class: TransferCtrl")
class TransferCtrlITest {

    @Autowired
    private MockMvc client;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Should get account for a given request")
    void givenRequest_whenGetAccount_thenShouldFindAccount() throws Exception {
        //given
        long accountNumberFrom = 123L;
        long accountNumberTo = 321L;
        BigDecimal amountToTransfer = TEN;

        mockRepository(accountNumberFrom, accountNumberTo);

        String body = "{ " +
                "\"accountNumberFrom\": " + accountNumberFrom + ", " +
                "\"accountNumberTo\": " + accountNumberTo + ", " +
                "\"amountToTransfer\": " + amountToTransfer +
                " }";

        //when
        client.perform(put("/api/account/transfer")
                .contentType(APPLICATION_JSON)
                .content(body)
                .accept(APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value("Success"));

        verify(accountRepository, times(2)).save(any());
    }

    private void mockRepository(long accountNumberFrom,
                                long accountNumberTo) {
        when(accountRepository.findByAccountNumber(eq(accountNumberFrom)))
                .thenReturn(AccountEntity.builder()
                        .name("John Doe")
                        .balance(TEN.multiply(TEN))
                        .accountNumber(accountNumberFrom)
                        .build());
        when(accountRepository.findByAccountNumber(eq(accountNumberTo)))
                .thenReturn(AccountEntity.builder()
                        .name("Mary Queen")
                        .balance(TEN.multiply(TEN))
                        .accountNumber(accountNumberTo)
                        .build());
    }
}
