package com.hsin.domain.currency;


import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CurrencyId implements Serializable {
    private String currencyCodeFrom;

    private String currencyCodeTo;

}
