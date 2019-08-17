package com.revolut.ibank.web.service;

import com.revolut.ibank.data.service.CreateAccountService;
import com.revolut.ibank.web.request.NewAccountRequest;
import com.revolut.ibank.web.service.response.NewAccountResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NewAccountAPIServiceImpl implements NewAccountAPIService {

    private static final String SUCCESS_MESSAGE = "Success";

    private CreateAccountService createAccountService;

    @Override
    public NewAccountResponse createNewAccount(@NonNull NewAccountRequest request) {

        Long accountNumber = createAccountService
                .save(request.getClientName(), request.getInitialBalance());

        return NewAccountResponse.builder()
                .accountNumber(accountNumber)
                .message(SUCCESS_MESSAGE)
                .build();
    }
}
