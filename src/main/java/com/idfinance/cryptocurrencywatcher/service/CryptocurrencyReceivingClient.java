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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
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
    private static final BigDecimal VERIFICATION_PERCENTAGE = new BigDecimal("1.0");

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
                    .divide(cryptocurrency.getPriceUsd(), 10, RoundingMode.HALF_UP)).doubleValue();
            BigDecimal excessPercentage = getExcessPercentage(priceRatio);

            if (excessPercentage.compareTo(VERIFICATION_PERCENTAGE) > 0) {
                double priceRatioWithRegistration = (checkUser.getPriceUsdUponRegistration()
                        .divide(cryptocurrency.getPriceUsd(), 10, RoundingMode.HALF_UP)).doubleValue();
                BigDecimal excessPercentageRegistration = getExcessPercentage(priceRatioWithRegistration);

                String exchangeRateDirection =
                        excessPercentageRegistration.compareTo(VERIFICATION_PERCENTAGE) > 0 ? "\u2193" : "\u2191";

                log.warn("COIN: {} {}, percent: {}, {}", checkUser.getSymbol(), exchangeRateDirection,
                        excessPercentageRegistration, checkUser.getName());
            }

            checkUser.setPriceUsd(cryptocurrency.getPriceUsd());
            log.info("Updated data: {}", checkUser);
            userRepository.save(checkUser);
        }
    }

    private BigDecimal getExcessPercentage(double value) {
        return new BigDecimal(String.valueOf(Math.abs(1.0 - value) * 100));
    }
}
