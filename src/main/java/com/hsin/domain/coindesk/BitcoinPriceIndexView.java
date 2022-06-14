package com.hsin.domain.coindesk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Embeddable
@Subselect(
        " SELECT rownum id," +
                "b.id bpi_id," +
                "c.currency_code_to currency_code," +
                "ROUND(b.rate * conversion_rate, 4) AS rate," +
                "cn.currency_name description," +
                "b.submission_date" +
                " FROM " +
                "    tb_bpi b" +
                " LEFT JOIN tb_currency c ON" +
                "    b.currency_code = c.currency_code_from" +
                " LEFT JOIN tb_currency_name cn ON" +
                "    c.currency_code_to = cn.currency_code"
)
public class BitcoinPriceIndexView {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bpi_id")
    @JsonIgnore
    private Long bpiId;

    @Column(name = "currency_code", length = 3, nullable = false) // JPA
    @JsonProperty("code")
    @ApiModelProperty(value = "Currency code", required = true)
    private String currencyCode;

    @Column(name = "rate", precision = 19, scale = 4, nullable = false) // JPA
    @JsonProperty("rate_float")
    @ApiModelProperty(value = "Rate in float", required = true)
    private BigDecimal rate;

    @Column(name = "description", length = 50, nullable = false) // JPA
    @JsonProperty("description")
    @ApiModelProperty(value = "Description", required = true)
    private String description;

    @Column(name = "submission_date")
    @ApiModelProperty(value = "Submission Date", required = true)
    @JsonIgnore
    private Date submissionDate = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BitcoinPriceIndexView that = (BitcoinPriceIndexView) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}