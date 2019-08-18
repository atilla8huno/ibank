package com.revolut.ibank.web.ctrl;

import com.revolut.ibank.web.service.GetAccountAPIService;
import com.revolut.ibank.web.service.request.GetAccountRequest;
import com.revolut.ibank.web.service.response.GetAccountResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        value = "/api/account",
        produces = APPLICATION_JSON_VALUE
)
@AllArgsConstructor
class GetAccountCtrl {

    private GetAccountAPIService apiService;

    @GetMapping("/{accountNumber}")
    @ResponseBody
    GetAccountResponse createNewAccount(@PathVariable Long accountNumber) {
        return apiService.getAccount(new GetAccountRequest(accountNumber));
    }
}
