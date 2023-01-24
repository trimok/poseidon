package com.nnk.springboot.domain;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Trade domain object
 * 
 * @author trimok
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer id;

    /**
     * account
     */
    @NotEmpty(message = "account is mandatory")
    private String account;

    /**
     * type
     */
    @NotEmpty(message = "type is mandatory")
    private String type;

    /**
     * buyQuantity
     */
    @NotNull(message = "buyQuantity is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    @Column(name = "buyQuantity")
    private Double buyQuantity;

    /**
     * sellQuantity
     */
    @NotNull(message = "sellQuantity is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    @Column(name = "sellQuantity")
    private Double sellQuantity;

    /**
     * buyPrice
     */
    @NotNull(message = "buyPrice is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    @Column(name = "buyPrice")
    private Double buyPrice;

    /**
     * sellPrice
     */
    @NotNull(message = "sellPrice is mandatory")
    @Column(name = "sellPrice")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    private Double sellPrice;

    /**
     * benchmark
     */
    @NotEmpty(message = "benchmark is mandatory")
    private String benchmark;

    /**
     * tradeDate
     */
    @NotNull(message = "tradeDate is mandatory")
    @Column(name = "tradeDate")
    private Timestamp tradeDate = Timestamp.from(Instant.now());

    /**
     * security
     */
    @NotEmpty(message = "security is mandatory")
    private String security;

    /**
     * status
     */
    @NotEmpty(message = "status is mandatory")
    private String status;

    /**
     * trader
     */
    @NotEmpty(message = "trader is mandatory")
    private String trader;

    /**
     * book
     */
    @NotEmpty(message = "book is mandatory")
    private String book;

    /**
     * creationName
     */
    @NotEmpty(message = "creationName is mandatory")
    @Column(name = "creationName")
    private String creationName;

    /**
     * creationDate
     */
    @NotNull(message = "creationDate is mandatory")
    @Column(name = "creationDate")
    private Timestamp creationDate = Timestamp.from(Instant.now());

    /**
     * revisionName
     */
    @NotEmpty(message = "revisionName is mandatory")
    @Column(name = "revisionName")
    private String revisionName;

    /**
     * revisionDate
     */
    @NotNull(message = "revisionDate is mandatory")
    @Column(name = "revisionDate")
    private Timestamp revisionDate = Timestamp.from(Instant.now());

    /**
     * dealName
     */
    @NotEmpty(message = "dealName is mandatory")
    @Column(name = "dealName")
    private String dealName;

    /**
     * dealType
     */
    @NotEmpty(message = "dealType is mandatory")
    @Column(name = "dealType")
    private String dealType;

    /**
     * sourceListId
     */
    @NotEmpty(message = "sourceListId is mandatory")
    @Column(name = "sourceListId")
    private String sourceListId;

    /**
     * side
     */
    @NotEmpty(message = "side is mandatory")
    private String side;

    /**
     * equals
     */
    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;

	if (!(o instanceof Trade))
	    return false;

	Trade other = (Trade) o;

	return id != null &&
		id.equals(other.getId());
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode() {
	return getClass().hashCode();
    }
}
