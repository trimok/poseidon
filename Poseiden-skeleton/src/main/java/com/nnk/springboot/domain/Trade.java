package com.nnk.springboot.domain;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer id;

    @NotEmpty(message = "account is mandatory")
    private String account;

    @NotEmpty(message = "type is mandatory")
    private String type;

    @NotNull(message = "buyQuantity is mandatory")
    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @NotNull(message = "sellQuantity is mandatory")
    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @NotNull(message = "buyPrice is mandatory")
    @Column(name = "buyPrice")
    private Double buyPrice;

    @NotNull(message = "sellPrice is mandatory")
    @Column(name = "sellPrice")
    private Double sellPrice;

    @NotEmpty(message = "benchmark is mandatory")
    private String benchmark;

    @NotNull(message = "tradeDate is mandatory")
    @Column(name = "tradeDate")
    private Timestamp tradeDate = Timestamp.from(Instant.now());

    @NotEmpty(message = "security is mandatory")
    private String security;

    @NotEmpty(message = "status is mandatory")
    private String status;

    @NotEmpty(message = "trader is mandatory")
    private String trader;

    @NotEmpty(message = "book is mandatory")
    private String book;

    @NotEmpty(message = "creationName is mandatory")
    @Column(name = "creationName")
    private String creationName;

    @NotNull(message = "creationDate is mandatory")
    @Column(name = "creationDate")
    private Timestamp creationDate = Timestamp.from(Instant.now());

    @NotEmpty(message = "revisionName is mandatory")
    @Column(name = "revisionName")
    private String revisionName;

    @NotNull(message = "revisionDate is mandatory")
    @Column(name = "revisionDate")
    private Timestamp revisionDate = Timestamp.from(Instant.now());

    @NotEmpty(message = "dealName is mandatory")
    @Column(name = "dealName")
    private String dealName;

    @NotEmpty(message = "dealType is mandatory")
    @Column(name = "dealType")
    private String dealType;

    @NotEmpty(message = "sourceListId is mandatory")
    @Column(name = "sourceListId")
    private String sourceListId;

    @NotEmpty(message = "side is mandatory")
    private String side;
}
