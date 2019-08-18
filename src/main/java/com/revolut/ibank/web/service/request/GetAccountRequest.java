package com.revolut.ibank.web.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAccountRequest {

    private Long accountNumber;
}
