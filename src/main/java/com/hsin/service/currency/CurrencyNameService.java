package com.hsin.service.currency;

import com.hsin.domain.currency.CurrencyName;
import com.hsin.repository.currency.CurrencyNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CurrencyNameService {
    @Autowired
    CurrencyNameRepository currencyNameRepository;

    public List<CurrencyName> findAll() {
        return currencyNameRepository.findAll();
    }

    public Optional<CurrencyName> findByCurrencyCode(String currencyCode) {
        return currencyNameRepository.findByCurrencyCode(currencyCode);
    }

    public int deleteByCurrencyCode(String currencyCode) {
        return currencyNameRepository.deleteByCurrencyCode(currencyCode);
    }

    public CurrencyName save(CurrencyName currencyName) {
        return currencyNameRepository.save(currencyName);
    }

    public void deleteAll() {
        currencyNameRepository.deleteAll();
    }
}
