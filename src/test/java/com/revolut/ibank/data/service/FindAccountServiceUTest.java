package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test suite of the class: FindAccountService")
class FindAccountServiceUTest {

    private FindAccountService service;

    @Mock
    private AccountRepository repository;

    @BeforeEach
    void setup() {
        service = new AccountEntityService(repository);
    }

    @Test
    @DisplayName("Should NOT accept invalid account number when finding by account number")
    void givenInvalidAccountNumber_whenFindByAccountNumber_thenShouldThrowException() {
        //given
        Long accountNumber = null;

        //when
        Executable findByAccountNumber =
                () -> service.findByAccountNumber(accountNumber);

        //then
        assertThrows(IllegalArgumentException.class, findByAccountNumber);
    }

    @Test
    @DisplayName("Should find account by account number")
    void givenAccountNumber_whenFindByAccountNumber_thenShouldFindEntity() {
        //given
        Long accountNumber = 123123L;
        mockRepository(accountNumber);

        //when
        Optional<AccountEntity> accountEntity
                = service.findByAccountNumber(accountNumber);

        //then
        assertNotNull(accountEntity);
        assertTrue(accountEntity.isPresent());
        assertEquals(accountNumber, accountEntity.get().getAccountNumber());

        verify(repository).findByAccountNumber(eq(accountNumber));
    }

    private void mockRepository(Long accountNumber) {
        when(repository.findByAccountNumber(eq(accountNumber)))
                .thenReturn(new AccountEntity("AB", accountNumber, TEN));
    }
}
