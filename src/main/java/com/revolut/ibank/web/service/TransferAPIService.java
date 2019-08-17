package com.revolut.ibank.web.service;

import com.revolut.ibank.web.service.request.TransferRequest;
import com.revolut.ibank.web.service.response.TransferResponse;

public interface TransferAPIService {

    TransferResponse transfer(TransferRequest transferRequest);
}
