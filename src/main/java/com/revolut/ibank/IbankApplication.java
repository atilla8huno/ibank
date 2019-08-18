package com.revolut.ibank;

import com.revolut.ibank.data.config.IbankApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({IbankApplicationConfig.class})
public class IbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbankApplication.class, args);
    }
}
