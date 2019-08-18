package com.revolut.ibank.web.ctrl;

import com.revolut.ibank.web.request.NewAccountRequest;
import com.revolut.ibank.web.service.NewAccountAPIService;
import com.revolut.ibank.web.service.response.NewAccountResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        value = "/api/account",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@AllArgsConstructor
class NewAccountCtrl {

    private NewAccountAPIService apiService;

    @PostMapping
    @ResponseBody
    NewAccountResponse createNewAccount(@RequestBody NewAccountRequest request) {
        return apiService.createNewAccount(request);
    }
}
