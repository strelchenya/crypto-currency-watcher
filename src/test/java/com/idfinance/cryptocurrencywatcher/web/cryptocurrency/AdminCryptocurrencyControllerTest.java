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
import static com.idfinance.cryptocurrencywatcher.web.user.UserTestData.admin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminCryptocurrencyControllerTest extends AbstractControllerTest {

    public static final String ADMIN_CRYPTOCURRENCY_REST_URL =
            AdminCryptocurrencyController.ADMIN_CRYPTOCURRENCY_REST_URL + '/';

    @Test
    void findBySymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "BTC")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CRYPTOCURRENCY_MATCHER.contentJson(
                        new Cryptocurrency(CRYPTOCURRENCY_ID_1, "Bitcoin", "BTC", new BigDecimal("38292.13"),
                                LocalDateTime.now())));
    }

    @Test
    void findByInvalidSymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "VTC")
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void findByNotFoundSymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "")
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void findByInvalidParam() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("nosymbo", "BTC")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void findByLowerCaseSymbol() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_CRYPTOCURRENCY_REST_URL + "/by-symbol")
                .param("symbol", "btc")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CRYPTOCURRENCY_MATCHER.contentJson(
                        new Cryptocurrency(CRYPTOCURRENCY_ID_1, "Bitcoin", "BTC", new BigDecimal("38292.13"),
                                LocalDateTime.now())));
    }

    @Test
    void deleteById() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_CRYPTOCURRENCY_REST_URL + CRYPTOCURRENCY_ID_2)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteByInvalidId() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_CRYPTOCURRENCY_REST_URL + NOT_FOUND_CRYPTOCURRENCY)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
