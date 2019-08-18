package com.revolut.ibank.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity)) return false;
        AccountEntity that = (AccountEntity) o;
        return id != null
                ? Objects.equals(id, that.id)
                : name.equals(that.name) &&
                accountNumber.equals(that.accountNumber) &&
                balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return id != null
                ? Objects.hash(id)
                : Objects.hash(name, accountNumber, balance);
    }
}
