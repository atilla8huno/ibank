package com.revolut.ibank.web.ctrl;

import com.revolut.ibank.data.config.IbankApplicationConfig;
import com.revolut.ibank.web.request.NewAccountRequest;
import com.revolut.ibank.web.service.response.NewAccountResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@DisplayName("Test suite of the class: NewAccountCtrl")
@ContextConfiguration(classes = IbankApplicationConfig.class)
class NewAccountCtrlITest {

    @Autowired
    private WebTestClient client;

    @Disabled
    @DisplayName("Should create an account for a given request")
    void givenRequest_whenCreateNewAccount_thenShouldHaveCreatedAccount() {
        //given
        NewAccountRequest request = new NewAccountRequest("John Doe", TEN);

        //when
        client.post()
                .uri("/account")
                .body(fromObject(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBody(NewAccountResponse.class)
                .consumeWith(response -> {
                    NewAccountResponse responseBody = response.getResponseBody();

                    assertEquals("Success", responseBody.getMessage());
                    assertNotNull(responseBody.getAccountNumber());
                });
    }
}
