package org.malik.micro.currency_conversion_service.controller;

import lombok.RequiredArgsConstructor;
import org.malik.micro.currency_conversion_service.api.CurrencyConversion;
import org.malik.micro.currency_conversion_service.api.CurrencyExchange;
import org.malik.micro.currency_conversion_service.client.CurrencyExchangeClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyExchangeClient currencyExchangeClient;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable int quantity) {
        final CurrencyExchange exchangeValue = currencyExchangeClient.getExchangeValue(from, to);
        return CurrencyConversion.builder().from(from).to(to).conversion(exchangeValue.getConversionMultiple())
                .quantity(quantity).finalAmount(exchangeValue.getConversionMultiple().multiply(new BigDecimal(quantity)))
                .environment(exchangeValue.getEnvironment()).build();
    }

}
