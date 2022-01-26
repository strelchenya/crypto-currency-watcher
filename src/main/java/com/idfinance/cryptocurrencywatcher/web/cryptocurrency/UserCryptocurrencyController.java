package com.idfinance.cryptocurrencywatcher.web.cryptocurrency;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.service.CryptocurrencyService;
import com.idfinance.cryptocurrencywatcher.to.CryptocurrencyTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.idfinance.cryptocurrencywatcher.web.cryptocurrency.UserCryptocurrencyController.CRYPTOCURRENCY_REST_URL;

@Slf4j
@CacheConfig(cacheNames = "cryptocurrency")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CRYPTOCURRENCY_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Cryptocurrency Controller", description = "Controller for editing cryptocurrency by user.")
public class UserCryptocurrencyController {

    public static final String CRYPTOCURRENCY_REST_URL = "/api/v1/cryptocurrencies";

    private final CryptocurrencyService cryptocurrencyService;

    @Operation(summary = "Get cryptocurrency", description = "Get cryptocurrency by symbol.")
    @GetMapping("/by-symbol")
    public Cryptocurrency findBySymbol(@RequestParam("symbol") String symbol) {
        log.info("get cryptocurrency by symbol: {}", symbol);
        return cryptocurrencyService.findBySymbol(symbol);
    }

    @Cacheable
    @Operation(summary = "Get all cryptocurrencies", description = "Get all cryptocurrency for an authorized user.")
    @GetMapping
    public List<CryptocurrencyTo> getAll() {
        log.info("get all CryptocurrencyTo for user");
        return cryptocurrencyService.getAll();
    }
}
