package com.idfinance.cryptocurrencywatcher.web.cryptocurrency;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.idfinance.cryptocurrencywatcher.web.cryptocurrency.AdminCryptocurrencyController.ADMIN_CRYPTOCURRENCY_REST_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ADMIN_CRYPTOCURRENCY_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Cryptocurrency Controller", description = "Controller for editing cryptocurrency by admin.")
public class AdminCryptocurrencyController {
    public static final String ADMIN_CRYPTOCURRENCY_REST_URL = "/api/v1/admin/cryptocurrency";
}
