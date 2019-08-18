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

import static java.math.BigDecimal.TEN;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Test suite of the class: GetAccountCtrl")
class GetAccountCtrlITest {

    @Autowired
    private MockMvc client;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Should get account for a given request")
    void givenRequest_whenGetAccount_thenShouldFindAccount() throws Exception {
        //given
        long accountNumber = 123L;
        mockRepository(accountNumber);

        //when
        client.perform(get("/api/account/" + accountNumber)
                .accept(APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.balance").isNotEmpty())
                .andExpect(jsonPath("$.accountNumber").value(accountNumber));

    }

    private void mockRepository(long accountNumber) {
        when(accountRepository.findByAccountNumber(eq(accountNumber)))
                .thenReturn(AccountEntity.builder()
                        .name("John Doe")
                        .balance(TEN.multiply(TEN))
                        .accountNumber(accountNumber)
                        .build());
    }
}
