package com.revolut.ibank.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "accountNumber", "balance"})
public class AccountEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private Long accountNumber;

    @Setter
    @Column(nullable = false)
    private BigDecimal balance;

    @Version
    private Long version;

    public AccountEntity(String name,
                         Long accountNumber,
                         BigDecimal balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
