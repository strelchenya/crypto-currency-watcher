package com.idfinance.cryptocurrencywatcher.web.cryptocurrency;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.idfinance.cryptocurrencywatcher.web.TestUtil.userHttpBasic;
import static com.idfinance.cryptocurrencywatcher.web.cryptocurrency.CryptocurrencyTestData.*;
import static com.idfinance.cryptocurrencywatcher.web.user.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCryptocurrencyControllerTest extends AbstractControllerTest {

    public static final String USER_CRYPTOCURRENCY_REST_URL = UserCryptocurrencyController.CRYPTOCURRENCY_REST_URL;

    @Test
    void findBySymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "BTC")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CRYPTOCURRENCY_MATCHER.contentJson(
                        new Cryptocurrency(CRYPTOCURRENCY_ID_1, "Bitcoin", "BTC", new BigDecimal("38292.13"),
                                LocalDateTime.now())));
    }

    @Test
    void findByInvalidSymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "VTC")
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void findByNotFoundSymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "")
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void findByInvalidParam() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("nosymbo", "BTC")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void findByLowerCaseSymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "btc")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CRYPTOCURRENCY_MATCHER.contentJson(
                        new Cryptocurrency(CRYPTOCURRENCY_ID_1, "Bitcoin", "BTC", new BigDecimal("38292.13"),
                                LocalDateTime.now())));
    }

    @Test
    void getAllUserVoteTos() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_CRYPTOCURRENCY_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CRYPTOCURRENCY_TO_MATCHER.contentJson(getAllCryptocurrencyTo));
    }
}
