package com.revolut.ibank;

import com.revolut.ibank.web.ctrl.NewAccountCtrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SmokeTest {

    @Autowired
    private NewAccountCtrl controller;

    @Test
    @DisplayName("Should have loaded spring context")
    void givenSpringContext_whenAutowiring_thenShouldInjectFromContext() {
        assertNotNull(controller);
    }
}
