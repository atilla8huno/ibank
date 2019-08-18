package com.revolut.ibank.web.ctrl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Test suite of the class: NewAccountCtrl")
class NewAccountCtrlITest {

    @Autowired
    private MockMvc client;

    @Test
    @DisplayName("Should create an account for a given request")
    void givenRequest_whenCreateNewAccount_thenShouldHaveCreatedAccount() throws Exception {
        //given
        String body = "{ \"clientName\": \"John Doe\", \"initialBalance\": 500.0 }";

        //when
        client.perform(post("/api/account")
                .contentType(APPLICATION_JSON)
                .content(body)
                .accept(APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
//                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                .andExpect(header().string("Location", "/api/account/12345"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.accountNumber").isNumber());
    }
}
