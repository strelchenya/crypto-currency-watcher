package com.idfinance.cryptocurrencywatcher.web.user;

import com.idfinance.cryptocurrencywatcher.model.Role;
import com.idfinance.cryptocurrencywatcher.model.User;
import com.idfinance.cryptocurrencywatcher.util.JsonUtil;
import com.idfinance.cryptocurrencywatcher.web.MatcherFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class,
                    "registered", "password", "priceUsd", "priceUsdUponRegistration");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "user", "BTC", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", "ETH", Role.ADMIN, Role.USER);

    protected static User getNew() {
        return new User(null, "NewName", "new@gmail.com", "newPass", "SOL",
                new BigDecimal("98.09"), new BigDecimal("98.09"), new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", "ETH", new BigDecimal("2628.57"),
                new BigDecimal("2628.57"), new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
