package com.revolut.ibank.data.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test suite of the class: AccountEntity")
class AccountEntityUTest {

    @Test
    @DisplayName("Should have two equal instances based on ID")
    void givenTwoInstancesWithSameId_whenEqualsAndHashCode_thenShouldBeEqual() {
        //given
        Long id = 123L;
        AccountEntity instance1 = AccountEntity.builder().id(id).build();
        AccountEntity instance2 = AccountEntity.builder().id(id).build();

        //when
        boolean instance1IsEqualsTo2 = instance1.equals(instance2);
        boolean instance2IsEqualsTo1 = instance2.equals(instance1);
        int hashCode1 = instance1.hashCode();
        int hashCode2 = instance2.hashCode();

        //then
        assertTrue(instance1IsEqualsTo2);
        assertTrue(instance2IsEqualsTo1);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should have two equal instances based on name, accountNumber and balance")
    void givenTwoInstancesWithSameAttributes_whenEqualsAndHashCode_thenShouldBeEqual() {
        //given
        String name = "John Doe";
        Long accountNumber = 123L;
        BigDecimal balance = TEN;

        AccountEntity instance1 = AccountEntity.builder()
                .name(name).accountNumber(accountNumber).balance(balance).build();
        AccountEntity instance2 = AccountEntity.builder()
                .name(name).accountNumber(accountNumber).balance(balance).build();

        //when
        boolean instance1IsEqualsTo2 = instance1.equals(instance2);
        boolean instance2IsEqualsTo1 = instance2.equals(instance1);
        int hashCode1 = instance1.hashCode();
        int hashCode2 = instance2.hashCode();

        //then
        assertTrue(instance1IsEqualsTo2);
        assertTrue(instance2IsEqualsTo1);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should have two different instances based on ID")
    void givenTwoInstancesWithDifferentId_whenEqualsAndHashCode_thenShouldNotBeEqual() {
        //given
        Long id1 = 123L;
        Long id2 = 321L;
        AccountEntity instance1 = AccountEntity.builder().id(id1).build();
        AccountEntity instance2 = AccountEntity.builder().id(id2).build();

        //when
        boolean instance1IsEqualsTo2 = instance1.equals(instance2);
        boolean instance2IsEqualsTo1 = instance2.equals(instance1);
        int hashCode1 = instance1.hashCode();
        int hashCode2 = instance2.hashCode();

        //then
        assertFalse(instance1IsEqualsTo2);
        assertFalse(instance2IsEqualsTo1);
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should have two different instances based on name, accountNumber and balance")
    void givenTwoInstancesWithDifferentAttributes_whenEqualsAndHashCode_thenShouldNotBeEqual() {
        //given
        String name = "John Doe";
        Long accountNumber = 123L;
        BigDecimal balance1 = TEN;
        BigDecimal balance2 = ONE;

        AccountEntity instance1 = AccountEntity.builder()
                .name(name).accountNumber(accountNumber).balance(balance1).build();
        AccountEntity instance2 = AccountEntity.builder()
                .name(name).accountNumber(accountNumber).balance(balance2).build();

        //when
        boolean instance1IsEqualsTo2 = instance1.equals(instance2);
        boolean instance2IsEqualsTo1 = instance2.equals(instance1);
        int hashCode1 = instance1.hashCode();
        int hashCode2 = instance2.hashCode();

        //then
        assertFalse(instance1IsEqualsTo2);
        assertFalse(instance2IsEqualsTo1);
        assertNotEquals(hashCode1, hashCode2);
    }
}
