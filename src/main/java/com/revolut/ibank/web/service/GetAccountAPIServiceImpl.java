package com.revolut.ibank.web.service;

import com.revolut.ibank.data.service.FindAccountService;
import com.revolut.ibank.domain.Account;
import com.revolut.ibank.domain.PersonalAccount;
import com.revolut.ibank.web.service.request.GetAccountRequest;
import com.revolut.ibank.web.service.response.GetAccountResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAccountAPIServiceImpl implements GetAccountAPIService {

    private static final String SUCCESS_MESSAGE = "Success";

    private FindAccountService findAccountService;

    @Override
    public GetAccountResponse getAccount(@NonNull GetAccountRequest request) {

        return findAccountService.findByAccountNumber(request.getAccountNumber())
                .map(this::mapToResponse)
                .orElseThrow(IllegalStateException::new);
    }

    private GetAccountResponse mapToResponse(Account account) {
        return GetAccountResponse.builder()
                .name(account instanceof PersonalAccount
                        ? ((PersonalAccount) account).getName()
                        : null)
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .message(SUCCESS_MESSAGE)
                .build();
    }
}
