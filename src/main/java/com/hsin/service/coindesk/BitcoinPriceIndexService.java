package com.hsin.service.coindesk;

import com.hsin.domain.coindesk.BitcoinPriceIndex;
import com.hsin.domain.coindesk.BitcoinPriceIndexView;
import com.hsin.repository.coindesk.BitcoinPriceIndexRepository;
import com.hsin.repository.coindesk.BitcoinPriceIndexViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BitcoinPriceIndexService {
    @Autowired
    BitcoinPriceIndexRepository bitcoinPriceIndexRepository;

    @Autowired
    BitcoinPriceIndexViewRepository bitcoinPriceIndexViewRepository;

    public BitcoinPriceIndex save(BitcoinPriceIndex entity) {
        return bitcoinPriceIndexRepository.save(entity);
    }

    public List<BitcoinPriceIndexView> findByBpiId(Long id) {
        return bitcoinPriceIndexViewRepository.findByBpiId(id);
    }
}
