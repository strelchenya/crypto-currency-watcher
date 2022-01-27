package com.idfinance.cryptocurrencywatcher.web.cryptocurrency;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.service.CryptocurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.idfinance.cryptocurrencywatcher.web.cryptocurrency.AdminCryptocurrencyController.ADMIN_CRYPTOCURRENCY_REST_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ADMIN_CRYPTOCURRENCY_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Cryptocurrency Controller", description = "Controller for editing cryptocurrency by admin.")
public class AdminCryptocurrencyController {
    public static final String ADMIN_CRYPTOCURRENCY_REST_URL = "/api/v1/admin/cryptocurrency";

    private final CryptocurrencyService cryptocurrencyService;

    @Operation(summary = "Get cryptocurrency", description = "Get cryptocurrency by symbol.")
    @GetMapping("/by-symbol")
    public Cryptocurrency findBySymbol(@RequestParam("symbol") String symbol) {
        log.info("get cryptocurrency by symbol: {}", symbol);
        return cryptocurrencyService.findBySymbol(symbol);
    }

    @Operation(summary = "Delete cryptocurrency", description = "Removes cryptocurrency by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete cryptocurrency {}", id);
        cryptocurrencyService.delete(id);
    }
}
