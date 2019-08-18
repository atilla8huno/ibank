package com.revolut.ibank.web.service;

import com.revolut.ibank.web.service.request.GetAccountRequest;
import com.revolut.ibank.web.service.response.GetAccountResponse;

public interface GetAccountAPIService {

    GetAccountResponse getAccount(GetAccountRequest request);
}
