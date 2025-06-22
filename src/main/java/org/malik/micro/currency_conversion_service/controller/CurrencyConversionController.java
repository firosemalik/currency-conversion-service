package org.malik.micro.currency_conversion_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.malik.micro.currency_conversion_service.api.CurrencyConversion;
import org.malik.micro.currency_conversion_service.api.CurrencyExchange;
import org.malik.micro.currency_conversion_service.client.CurrencyExchangeClient;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.http.HttpHeaders;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionController {

    private final CurrencyExchangeClient currencyExchangeClient;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable int quantity,
                                                  HttpServletRequest request) {
        final CurrencyExchange exchangeValue = currencyExchangeClient.getExchangeValue(from, to);

        request.getHeaderNames().asIterator().forEachRemaining(h -> log.info("values : {}", h.toLowerCase()));

        return CurrencyConversion.builder().from(from).to(to).conversion(exchangeValue.getConversionMultiple())
                .quantity(quantity).finalAmount(exchangeValue.getConversionMultiple().multiply(new BigDecimal(quantity)))
                .environment(exchangeValue.getEnvironment()).build();
    }

}
