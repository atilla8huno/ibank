package com.revolut.ibank.data.service;

import com.revolut.ibank.data.entity.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Test suite of the class: AccountRepository")
class AccountRepositoryITest {

    @Autowired
    private AccountRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should find account by account number")
    void givenExistingAccountNumber_whenFindByAccountNumber_thenShouldFindAccount() {
        //given
        Long accountNumber = 123123L;
        AccountEntity accountEntity = new AccountEntity("Costumer A", accountNumber, TEN);
        repository.save(accountEntity);

        //when
        AccountEntity entityFound = repository.findByAccountNumber(accountNumber);

        //then
        assertNotNull(entityFound);
        assertEquals(accountNumber, entityFound.getAccountNumber());
    }

    @Test
    @DisplayName("Should increase version when saving multiple times")
    void givenAccountEntity_whenSavingMultipleTimes_thenShouldIncreaseVersion() {
        //given
        AccountEntity accountEntity = new AccountEntity( "Atilla Barros", 123123L, TEN);
        accountEntity = repository.save(accountEntity);
        Long version = accountEntity.getVersion();

        //when
        accountEntity.setName("Costumer Name");
        AccountEntity secondSaving = repository.save(accountEntity);

        //then
        assertNotNull(version);
        assertTrue(version < secondSaving.getVersion());
    }

    @ParameterizedTest
    @MethodSource("invalidParametersToSaveAccount")
    @DisplayName("Should NOT accept invalid parameters when saving")
    void givenInvalidParameters_whenSave_thenShouldThrowException(
            String name,
            Long accountNumber,
            BigDecimal balance) {
        //given invalid parameters
        AccountEntity accountEntity =
                new AccountEntity(name, accountNumber, balance);

        //when
        Executable saving = () -> repository.save(accountEntity);

        //then
        assertThrows(Exception.class, saving);
    }

    @Test
    @DisplayName("Should NOT accept duplicated account number when saving")
    void givenDuplicatedAccounts_whenSave_thenShouldThrowException() {
        //given
        String name1 = "Atilla Barros";
        String name2 = "Atilla Barros";
        Long accountNumber = 123123L;
        BigDecimal balance = TEN;

        AccountEntity account1 =
                new AccountEntity(name1, accountNumber, balance);
        AccountEntity account2 =
                new AccountEntity(name2, accountNumber, balance);

        account1 = repository.save(account1);
        assertNotNull(account1);

        //when
        Executable saveAccount2 = () -> repository.save(account2);

        //then
        assertThrows(DataIntegrityViolationException.class, saveAccount2);
    }

    @Test
    @DisplayName("Should save account entity")
    void givenAccountEntity_whenSave_thenShouldSaveAccount() {
        //given
        String name = "Atilla Barros";
        Long accountNumber = 123123L;
        BigDecimal balance = TEN;
        AccountEntity accountEntity =
                new AccountEntity(name, accountNumber, balance);

        //when
        AccountEntity savedEntity = repository.save(accountEntity);

        //then
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
        assertEquals(name, savedEntity.getName());
        assertEquals(accountNumber, savedEntity.getAccountNumber());
        assertEquals(balance, savedEntity.getBalance());
    }

    private static Stream<Arguments> invalidParametersToSaveAccount() {
        return Stream.of(
                arguments("AB", 123L, null),
                arguments("AB", null, TEN),
                arguments(null, 333L, TEN)
        );
    }
}
