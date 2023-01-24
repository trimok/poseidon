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
 * BidList domain object
 * 
 * @author trimok
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    /**
     * Constructor
     * 
     * @param account     : account
     * @param type        : type
     * @param bidQuantity : bidQuantity
     */
    public BidList(String account, String type, Double bidQuantity) {
	this.account = account;
	this.type = type;
	this.bidQuantity = bidQuantity;
    }

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
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
     * bidQuantity
     */
    @NotNull(message = "bidQuantity is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    @Column(name = "bidQuantity")
    private Double bidQuantity;

    /**
     * askQuantity
     */
    @NotNull(message = "askQuantity is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    @Column(name = "askQuantity")
    private Double askQuantity;

    /**
     * bid
     */
    @NotNull(message = "bid is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    private Double bid;

    /**
     * ask
     */
    @NotNull(message = "ask is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    private Double ask;

    /**
     * benchmark
     */
    @NotEmpty(message = "benchmark is mandatory")
    private String benchmark;

    /**
     * bidListDate
     */
    @NotNull(message = "bidListDate is mandatory")
    @Column(name = "bidListDate")
    private Timestamp bidListDate = Timestamp.from(Instant.now());

    /**
     * commentary
     */
    @NotEmpty(message = "commentary is mandatory")
    private String commentary;

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

	if (!(o instanceof BidList))
	    return false;

	BidList other = (BidList) o;

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
