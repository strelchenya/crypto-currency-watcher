package com.idfinance.cryptocurrencywatcher.web.user;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.model.User;
import com.idfinance.cryptocurrencywatcher.repository.UserRepository;
import com.idfinance.cryptocurrencywatcher.service.CryptocurrencyService;
import com.idfinance.cryptocurrencywatcher.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    @Autowired
    protected CryptocurrencyService cryptocurrencyService;
    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    protected User prepareAndSave(User user) {
        log.info("user: {}", user);
        String symbol = user.getSymbol();
        Cryptocurrency cryptocurrency = cryptocurrencyService.findBySymbol(symbol);
        log.info("Get a user-selected symbol: {} cryptocurrency: {}", symbol, cryptocurrency);
        user.setPriceUsd(cryptocurrency.getPriceUsd());

        if (user.getId() != null) {
            User userWithDataBase = repository.getById(user.getId());
            if (userWithDataBase.getSymbol().equalsIgnoreCase(user.getSymbol())) {
                user.setPriceUsdUponRegistration(userWithDataBase.getPriceUsdUponRegistration());
                user.setRegistered(userWithDataBase.getRegistered());
            } else {
                user.setPriceUsdUponRegistration(user.getPriceUsd());
                user.setRegistered(new Date());
            }
        } else {
            user.setPriceUsdUponRegistration(user.getPriceUsd());
        }
        return repository.save(UserUtil.prepareToSave(user));
    }
}
