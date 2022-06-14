package com.hsin.domain.coindesk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * DROP TABLE IF EXISTS public.tb_bpi;
 * CREATE TABLE public.tb_bpi (
 * id              BIGINT AUTO_INCREMENT NOT NULL,
 * country_code    VARCHAR(3) NOT NULL,
 * rate            NUMBER NOT NULL,
 * submission_date TIMESTAMP NOT NULL,
 * PRIMARY KEY (id)
 * );
 *
 * @author Hsu
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_bpi")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BitcoinPriceIndex {
    @Id // JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "submission_date", nullable = false) // JPA
    @JsonIgnore
    @ApiModelProperty(value = "Submission Date", required = true)
    private Date submissionDate = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BitcoinPriceIndex that = (BitcoinPriceIndex) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}