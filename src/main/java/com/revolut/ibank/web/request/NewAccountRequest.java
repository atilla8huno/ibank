package com.revolut.ibank.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class NewAccountRequest {

    private String clientName;
    private BigDecimal initialBalance;
}
