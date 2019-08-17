package com.revolut.ibank.web.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NewAccountResponse {

    private Long accountNumber;
    private String message;
}
