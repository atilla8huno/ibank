package com.revolut.ibank.web.service;

import com.revolut.ibank.data.service.FindAccountService;
import com.revolut.ibank.data.service.UpdateBalanceAccountService;
import com.revolut.ibank.domain.Account;
import com.revolut.ibank.web.service.request.TransferRequest;
import com.revolut.ibank.web.service.response.TransferResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;

import static java.util.Arrays.asList;

@Service
@AllArgsConstructor
public class TransferAPIServiceImpl implements TransferAPIService {

    private static final String SUCCESS_MESSAGE = "Success";

    private FindAccountService findAccountService;
    private UpdateBalanceAccountService updateBalanceAccountService;

    @Override
    public TransferResponse transfer(@NonNull TransferRequest transferRequest) {

        final Long accountNumberTo = transferRequest.getAccountNumberTo();
        final Long accountNumberFrom = transferRequest.getAccountNumberFrom();
        final BigDecimal amountToTransfer = transferRequest.getAmountToTransfer();

        Account accountTo = findAccount(accountNumberTo);
        Account accountFrom = findAccount(accountNumberFrom);

        accountTo.transferFrom(accountFrom, amountToTransfer);

        updateAccounts(accountTo, accountFrom);

        return TransferResponse.builder()
                .message(SUCCESS_MESSAGE)
                .build();
    }

    private void updateAccounts(Account... accounts) {
        updateBalanceAccountService
                .updateBalance(new HashSet<>(asList(accounts)));
    }

    private Account findAccount(Long accountNumber) {
        return findAccountService
                .findByAccountNumber(accountNumber)
                .orElseThrow(IllegalStateException::new);
    }
}
