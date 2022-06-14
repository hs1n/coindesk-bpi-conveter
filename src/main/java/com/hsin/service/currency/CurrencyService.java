package com.hsin.service.currency;

import com.hsin.domain.currency.Currency;
import com.hsin.repository.currency.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> findByCurrencyCodeFromAndCurrencyCodeTo(String currencyCodeFrom, String currencyCodeTo) {
        return currencyRepository.findByCurrencyCodeFromAndCurrencyCodeTo(currencyCodeFrom, currencyCodeTo);
    }

    public int deleteByCurrencyCodeFromAndCurrencyCodeTo(String currencyCodeFrom, String currencyCodeTo) {
        return currencyRepository.deleteByCurrencyCodeFromAndCurrencyCodeTo(currencyCodeFrom, currencyCodeTo);
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public void deleteAll() {
        currencyRepository.deleteAll();
    }

}
