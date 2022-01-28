package com.idfinance.cryptocurrencywatcher.web.cryptocurrency;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.to.CryptocurrencyTo;
import com.idfinance.cryptocurrencywatcher.web.MatcherFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CryptocurrencyTestData {
    public static final MatcherFactory.Matcher<Cryptocurrency> CRYPTOCURRENCY_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Cryptocurrency.class, "dateTime", "priceUsd");

    public static final MatcherFactory.Matcher<CryptocurrencyTo> CRYPTOCURRENCY_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(CryptocurrencyTo.class);

    public static final int NOT_FOUND_CRYPTOCURRENCY = 0;
    public static final int CRYPTOCURRENCY_ID_1 = 90;
    public static final int CRYPTOCURRENCY_ID_2 = 80;
    public static final int CRYPTOCURRENCY_ID_3 = 48543;

    public static final Cryptocurrency CRYPTOCURRENCY_1 = new Cryptocurrency(CRYPTOCURRENCY_ID_1, "Bitcoin",
            "BTC", new BigDecimal("38292.13"), LocalDateTime.now());
    public static final Cryptocurrency CRYPTOCURRENCY_2 = new Cryptocurrency(CRYPTOCURRENCY_ID_2, "Ethereum",
            "ETH", new BigDecimal("2628.57"), LocalDateTime.now());
    public static final Cryptocurrency CRYPTOCURRENCY_3 = new Cryptocurrency(CRYPTOCURRENCY_ID_3, "Solana",
            "SOL", new BigDecimal("98.09"), LocalDateTime.now());

    public static final CryptocurrencyTo CRYPTOCURRENCY_TO_1 =
            new CryptocurrencyTo(CRYPTOCURRENCY_ID_1, "Bitcoin", "BTC");
    public static final CryptocurrencyTo CRYPTOCURRENCY_TO_2 =
            new CryptocurrencyTo(CRYPTOCURRENCY_ID_2, "Ethereum", "ETH");
    public static final CryptocurrencyTo CRYPTOCURRENCY_TO_3 =
            new CryptocurrencyTo(CRYPTOCURRENCY_ID_3, "Solana", "SOL");

    public static final List<CryptocurrencyTo> getAllCryptocurrencyTo = List.of(CRYPTOCURRENCY_TO_1,
            CRYPTOCURRENCY_TO_2, CRYPTOCURRENCY_TO_3);
}
