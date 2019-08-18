package com.revolut.ibank.web.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class GetAccountResponse {

    private String name;
    private Long accountNumber;
    private BigDecimal balance;

    private String message;
}
