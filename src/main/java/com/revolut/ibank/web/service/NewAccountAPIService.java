package com.revolut.ibank.web.service;

import com.revolut.ibank.web.request.NewAccountRequest;
import com.revolut.ibank.web.service.response.NewAccountResponse;

public interface NewAccountAPIService {

    NewAccountResponse createNewAccount(NewAccountRequest request);
}
