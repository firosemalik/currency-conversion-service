package org.malik.micro.currency_conversion_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.malik.micro.currency_conversion_service.api.CurrencyConversion;
import org.malik.micro.currency_conversion_service.api.CurrencyExchange;
import org.malik.micro.currency_conversion_service.client.CurrencyExchangeClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionController {

    private final CurrencyExchangeClient currencyExchangeClient;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    @RateLimiter(name = "default", fallbackMethod = "fallBackResponse")
    public CurrencyConversion calculateConversion(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable int quantity,
                                                  HttpServletRequest request) {
        log.info("currency conversion called");
        final CurrencyExchange exchangeValue = currencyExchangeClient.getExchangeValue(from, to);
        log.info("currency exchange call completed {} -> {}", exchangeValue.getFrom(), exchangeValue.getTo());

        request.getHeaderNames().asIterator().forEachRemaining(h -> log.info("values : {}", h.toLowerCase()));

        return CurrencyConversion.builder().from(from).to(to).conversion(exchangeValue.getConversionMultiple())
                .quantity(quantity).finalAmount(exchangeValue.getConversionMultiple().multiply(new BigDecimal(quantity)))
                .environment(exchangeValue.getEnvironment()).build();
    }

    public CurrencyConversion fallBackResponse() {
        return CurrencyConversion.builder().environment("fallback activated").build();
    }

}
