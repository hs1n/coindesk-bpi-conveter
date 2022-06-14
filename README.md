# Coindesk BPI converter

## How to build

1. git clone https://github.com/th-hsu/coindesk.git
2. cd coindesk
3. `./mvnw spring-boot:run`

## How to run tests

1. build
   2 `./mvnw test`

## API Docs

* [SwaggerUI](http://localhost:8080/swagger-ui/index.html)

## Database

* [H2](http://localhost:8080/h2-console/)

## ER Diagram

```mermaid
erDiagram
    TB_BPI }o--|| VW_BPI : join
    TB_BPI {
        bigint id PK
        varchar currency_code PK "Currency code (e.g. USD, NTD,......)"
        number rate
        timestamp submission_date
    }
    TB_CURRENCY }o--|| VW_BPI : join
    TB_CURRENCY {
        bigint id
        varchar currency_code_from PK "Currency code (e.g. USD, NTD,......)"
        varchar currency_code_to PK "Currency code (e.g. USD, NTD,......)"
        number conversion_rate
        timestamp submission_date
    }
    TB_CURRENCY }o--o{ TB_CURRENCY_NAME : has
    TB_CURRENCY_NAME {
        bigint id
        varchar currency_code PK "Currency code (e.g. USD, NTD,......)"
        varchar currency_name "Currency name in chinese (e.g. 美金, 台幣,......)"
        timestamp submission_date
    }
```