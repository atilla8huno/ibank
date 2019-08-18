package com.revolut.ibank.web.ctrl;

import com.revolut.ibank.web.service.TransferAPIService;
import com.revolut.ibank.web.service.request.TransferRequest;
import com.revolut.ibank.web.service.response.TransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        value = "/api/account/transfer",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class TransferCtrl {

    private TransferAPIService apiService;

    @PutMapping
    @ResponseBody
    TransferResponse transfer(@RequestBody TransferRequest request) {
        return apiService.transfer(request);
    }
}
