package com.hsin.controller.currency;

import com.hsin.domain.currency.CurrencyName;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyNameControllerTest {

    @Autowired
    CurrencyNameController currencyNameController;

    static List<CurrencyName> currencyNames;

    @BeforeAll
    static void setup() {
        CurrencyName CurrencyNameTWD = new CurrencyName(null, "TWD", "臺幣", null);
        CurrencyName CurrencyNameJPY = new CurrencyName(null, "JPY", "日幣", null);
        CurrencyName CurrencyNameKRW = new CurrencyName(null, "KRW", "韓元", null);

        currencyNames = Arrays.asList(CurrencyNameTWD, CurrencyNameJPY, CurrencyNameKRW);
    }

    @Test
    @Order(1)
    @DisplayName("2. 測試呼叫新增幣別對應表資料 API。")
    void insert() {
        for (CurrencyName testCase : currencyNames) {
            ResponseEntity<CurrencyName> response = currencyNameController.insert(testCase.getCurrencyCode(), testCase.getCurrencyName());
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testCase.getCurrencyName(), response.getBody().getCurrencyName());
            assertEquals(testCase.getCurrencyCode(), response.getBody().getCurrencyCode());
        }
    }


    @Test
    @Order(2)
    @DisplayName("1. (單筆查詢)測試呼叫查詢幣別對應表資料 API，並顯示其內容。")
    void getOne() {
        for (CurrencyName testCase : currencyNames) {
            ResponseEntity<CurrencyName> response = currencyNameController.getOne(testCase.getCurrencyCode());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testCase.getCurrencyName(), response.getBody().getCurrencyName());
            assertEquals(testCase.getCurrencyCode(), response.getBody().getCurrencyCode());
        }
    }

    @Test
    @Order(3)
    @DisplayName("3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。")
    void update() {
        for (CurrencyName testCase : currencyNames) {
            ResponseEntity<CurrencyName> response = currencyNameController.update(testCase.getCurrencyCode(), testCase.getCurrencyName() + "M");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(Objects.nonNull(response.getBody()));
            System.out.println(response.getBody().toString());
            assertEquals(testCase.getCurrencyName() + "M", response.getBody().getCurrencyName());
            assertEquals(testCase.getCurrencyCode(), response.getBody().getCurrencyCode());
        }
    }

    @Test
    @Order(4)
    @DisplayName("1. (查詢全部)測試呼叫查詢幣別對應表資料 API，並顯示其內容。")
    void getAll() {
        ResponseEntity<List<CurrencyName>> response = currencyNameController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.nonNull(response.getBody()));
        System.out.println(response.getBody().toString());
        assertEquals(currencyNames.size(), response.getBody().size());
    }

    @Test
    @Order(5)
    @DisplayName("4. 測試呼叫刪除幣別對應表資料 API。")
    void delete() {
        for (CurrencyName testCase : currencyNames) {
            ResponseEntity<HttpStatus> response = currencyNameController.delete(testCase.getCurrencyCode());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Test
    @Order(6)
    void deleteAll() {
        ResponseEntity<HttpStatus> response = currencyNameController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}