package com.idfinance.cryptocurrencywatcher.service;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.model.Ticker;
import com.idfinance.cryptocurrencywatcher.model.User;
import com.idfinance.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import com.idfinance.cryptocurrencywatcher.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CryptocurrencyReceivingClient {
    private final RestTemplate restTemplate;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final UserRepository userRepository;

    private static final String SERVER_URL = "https://api.coinlore.net/api/ticker/?id=";
    private static final int TIMER = 60 * 1000;
    private static final int INIT_TIMER = 1000;
    private static final int VERIFICATION_PERCENTAGE = 1;

    @Scheduled(fixedDelay = TIMER, initialDelay = INIT_TIMER)
    public void getCryptocurrency() {
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.findAll();

        for (int i = 0; i < cryptocurrencies.size(); i++) {
            Cryptocurrency cryptocurrency = cryptocurrencies.get(i);
            Ticker[] tickers = null;
            try {
                tickers = restTemplate.getForObject(new URI(SERVER_URL + cryptocurrency.getId()), Ticker[].class);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mapperTickerToCryptocurrency(tickers, cryptocurrency);
        }
    }

    private void mapperTickerToCryptocurrency(Ticker[] tickers, Cryptocurrency cryptocurrency) {
        Ticker ticker = Objects.requireNonNull(tickers)[0];
        log.info("{} - {}", ticker, cryptocurrency);
        if (ticker.getSymbol().equalsIgnoreCase(cryptocurrency.getSymbol())) {
            cryptocurrency.setPriceUsd(ticker.getPrice_usd());
            cryptocurrency.setDateTime(LocalDateTime.now());
            if (!ticker.getName().equals(cryptocurrency.getName())) {
                cryptocurrency.setName(ticker.getName());
            }
            cryptocurrencyRepository.save(cryptocurrency);
        }
        checkingChangePriceOfCoinFromUser(cryptocurrency);
    }

    private void checkingChangePriceOfCoinFromUser(Cryptocurrency cryptocurrency) {
        log.info("{}", cryptocurrency);
        List<User> users = userRepository.findAllBySymbolOrderByName(cryptocurrency.getSymbol());
        for (User checkUser : users) {
            log.info("Outdated data: {}", checkUser);
            double priceRatio = (checkUser.getPriceUsd()
                    .divide(cryptocurrency.getPriceUsd(), 2, RoundingMode.HALF_UP)).doubleValue();
            int excessPercentage = (int) (Math.abs(1.0 - priceRatio) * 100);
            if (excessPercentage > VERIFICATION_PERCENTAGE) {
                double priceRatioWithRegistration = (checkUser.getPriceUsdUponRegistration()
                        .divide(cryptocurrency.getPriceUsd(), 2, RoundingMode.HALF_UP)).doubleValue();
                int excessPercentageRegistration = (int) (Math.abs(1.0 - priceRatioWithRegistration) * 100);
                String exchangeRateDirection = priceRatioWithRegistration > 1 ? "\u2193" : "\u2191";

                log.warn("COIN: {} {}, percent: {}, {}", checkUser.getSymbol(), exchangeRateDirection,
                        excessPercentageRegistration, checkUser.getName());
                checkUser.setPriceUsdUponRegistration(cryptocurrency.getPriceUsd());
                checkUser.setRegistered(new Date());
            }

            checkUser.setPriceUsd(cryptocurrency.getPriceUsd());
            log.info("Updated data: {}", checkUser);
            userRepository.save(checkUser);
        }
    }
}
