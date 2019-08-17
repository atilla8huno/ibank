package com.revolut.ibank.web.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TransferRequest {

    private Long accountNumberFrom;
    private Long accountNumberTo;
    private BigDecimal amountToTransfer;
}
