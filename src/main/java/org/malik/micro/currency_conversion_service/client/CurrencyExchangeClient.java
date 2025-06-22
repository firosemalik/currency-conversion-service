package org.malik.micro.currency_conversion_service.client;

import org.malik.micro.currency_conversion_service.api.CurrencyExchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeClient {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getExchangeValue(@PathVariable String from,
                                             @PathVariable String to);
}
