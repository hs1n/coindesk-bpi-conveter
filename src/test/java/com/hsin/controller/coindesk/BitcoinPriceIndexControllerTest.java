package com.hsin.controller.coindesk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BitcoinPriceIndexControllerTest {

    @Autowired
    BitcoinPriceIndexController bitcoinPriceIndexController;

    @Test
    @DisplayName("5. 測試呼叫 coindesk API，並顯示其內容。")
    void loadCoindeskBpiJSON() {
        String json = bitcoinPriceIndexController.loadCoindeskBpiJSON();
        System.out.println(bitcoinPriceIndexController.loadCoindeskBpiJSON());
        assertTrue(StringUtils.isNotBlank(json));
    }
}