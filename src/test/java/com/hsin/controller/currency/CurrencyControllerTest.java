package com.hsin.controller.currency;

import com.hsin.domain.currency.Currency;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
class CurrencyControllerTest {

    @Autowired
    CurrencyController currencyController;

    static List<Currency> currencies;

    @BeforeAll
    static void setup() {
        Currency currencyTWD = new Currency(null, "USD", "TWD", BigDecimal.valueOf(29.68), null);
        Currency currencyJPY = new Currency(null, "USD", "JPY", BigDecimal.valueOf(134.86), null);
        Currency currencyKRW = new Currency(null, "USD", "KRW", BigDecimal.valueOf(1290.95), null);

        currencies = Arrays.asList(currencyTWD, currencyJPY, currencyKRW);
    }

    @Test
    @Order(1)
    @DisplayName("2. 測試呼叫新增幣別對應表資料 API。")
    void insert() {
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
    @Order(2)
    @DisplayName("1. (單筆查詢)測試呼叫查詢幣別對應表資料 API，並顯示其內容。")
    void getOne() {
        for (Currency testCase : currencies) {
            ResponseEntity<Currency> response = currencyController.getOne(testCase.getCurrencyCodeFrom(), testCase.getCurrencyCodeTo());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testCase.getCurrencyCodeFrom(), response.getBody().getCurrencyCodeFrom());
            assertEquals(testCase.getCurrencyCodeTo(), response.getBody().getCurrencyCodeTo());
            assertEquals(0, testCase.getConversionRate().compareTo(response.getBody().getConversionRate()));
        }
    }

    @Test
    @Order(3)
    @DisplayName("3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。")
    void update() {
        for (Currency testCase : currencies) {
            ResponseEntity<Currency> response = currencyController.update(testCase.getCurrencyCodeFrom(), testCase.getCurrencyCodeTo(), testCase.getConversionRate().add(BigDecimal.ONE));
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testCase.getCurrencyCodeFrom(), response.getBody().getCurrencyCodeFrom());
            assertEquals(testCase.getCurrencyCodeTo(), response.getBody().getCurrencyCodeTo());
            assertEquals(0, testCase.getConversionRate().add(BigDecimal.ONE).compareTo(response.getBody().getConversionRate()));
        }
    }

    @Test
    @Order(4)
    @DisplayName("1. (查詢全部)測試呼叫查詢幣別對應表資料 API，並顯示其內容。")
    void getAll() {
        ResponseEntity<List<Currency>> response = currencyController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.nonNull(response.getBody()));
        System.out.println(response.getBody().toString());
        assertEquals(currencies.size(), response.getBody().size());
    }

    @Test
    @Order(5)
    @DisplayName("4. 測試呼叫刪除幣別對應表資料 API。")
    void delete() {
        for (Currency testCase : currencies) {
            ResponseEntity<HttpStatus> response = currencyController.delete(testCase.getCurrencyCodeFrom(), testCase.getCurrencyCodeTo());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Test
    @Order(6)
    void deleteAll() {
        ResponseEntity<HttpStatus> response = currencyController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}