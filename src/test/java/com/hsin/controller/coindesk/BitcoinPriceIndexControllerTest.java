package com.hsin.controller.coindesk;

import com.hsin.controller.currency.CurrencyController;
import com.hsin.controller.currency.CurrencyNameController;
import com.hsin.domain.currency.Currency;
import com.hsin.domain.currency.CurrencyName;
import com.hsin.model.CurrentPriceConverted;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BitcoinPriceIndexControllerTest {

    @Autowired
    CurrencyNameController currencyNameController;

    @Autowired
    CurrencyController currencyController;

    @Autowired
    BitcoinPriceIndexController bitcoinPriceIndexController;

    static List<Currency> currencies;
    static List<CurrencyName> currencyNames;

    @BeforeAll
    static void setup() {
        Currency currencyTWD = new Currency(null, "USD", "TWD", BigDecimal.valueOf(29.68), null);
        Currency currencyJPY = new Currency(null, "USD", "JPY", BigDecimal.valueOf(134.86), null);
        Currency currencyKRW = new Currency(null, "USD", "KRW", BigDecimal.valueOf(1290.95), null);

        currencies = Arrays.asList(currencyTWD, currencyJPY, currencyKRW);

        CurrencyName CurrencyNameTWD = new CurrencyName(null, "TWD", "臺幣", null);
        CurrencyName CurrencyNameJPY = new CurrencyName(null, "JPY", "日幣", null);
        CurrencyName CurrencyNameKRW = new CurrencyName(null, "KRW", "韓元", null);

        currencyNames = Arrays.asList(CurrencyNameTWD, CurrencyNameJPY, CurrencyNameKRW);
    }

    @Test
    @Order(1)
    @DisplayName("5. 測試呼叫 coindesk API，並顯示其內容。")
    void loadCoindeskBpiJSON() {
        String json = bitcoinPriceIndexController.loadCoindeskBpiJSON();
        System.out.println(bitcoinPriceIndexController.loadCoindeskBpiJSON());
        assertTrue(StringUtils.isNotBlank(json));
    }

    @Test
    @Order(2)
    @DisplayName("5.(Setup) 新增測試資料")
    void insertTestData() {
        for (CurrencyName testNameCase : currencyNames) {
            ResponseEntity<CurrencyName> response = currencyNameController.insert(testNameCase.getCurrencyCode(), testNameCase.getCurrencyName());
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testNameCase.getCurrencyName(), response.getBody().getCurrencyName());
            assertEquals(testNameCase.getCurrencyCode(), response.getBody().getCurrencyCode());
        }

        for (Currency testCase : currencies) {
            ResponseEntity<Currency> response = currencyController.insert(testCase.getCurrencyCodeFrom(), testCase.getCurrencyCodeTo(), testCase.getConversionRate());
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testCase.getCurrencyCodeFrom(), response.getBody().getCurrencyCodeFrom());
            assertEquals(testCase.getCurrencyCodeTo(), response.getBody().getCurrencyCodeTo());
            assertEquals(0, testCase.getConversionRate().compareTo(response.getBody().getConversionRate()));
        }
    }

    @Test
    @Order(3)
    @DisplayName("6. 測試呼叫資料轉換的 API，並顯示其內容。")
    void loadConvertedData() {

        ResponseEntity<CurrentPriceConverted> currentPriceConvertedResponseEntity = bitcoinPriceIndexController.loadConvertedData();
        System.out.println(currentPriceConvertedResponseEntity.getBody());
        assertEquals(HttpStatus.CREATED, currentPriceConvertedResponseEntity.getStatusCode());
        CurrentPriceConverted currentPriceConverted = currentPriceConvertedResponseEntity.getBody();
        assert currentPriceConverted != null;
        assertNotNull(currentPriceConverted.getChartName());
        assertNotNull(currentPriceConverted.getDisclaimer());
        assertNotNull(currentPriceConverted.getTime());
        assertNotNull(currentPriceConverted.getBpi());

    }

    @Test
    @Order(4)
    @DisplayName("5.(Cleanup) 移除測試資料")
    public void cleanup() {
        ResponseEntity<HttpStatus> currencyNameResponse = currencyNameController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, currencyNameResponse.getStatusCode());

        ResponseEntity<HttpStatus> currencyResponse = currencyController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, currencyResponse.getStatusCode());
    }
}