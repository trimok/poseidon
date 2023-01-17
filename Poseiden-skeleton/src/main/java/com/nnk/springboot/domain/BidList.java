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

@Entity
@Data
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    public BidList() {
	bidListDate = Timestamp.from(Instant.now());
	creationDate = Timestamp.from(Instant.now());
	revisionDate = Timestamp.from(Instant.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer id;

    @NotEmpty(message = "account is mandatory")
    private String account;

    @NotEmpty(message = "type is mandatory")
    private String type;

    @NotNull(message = "bidQuantity is mandatory")
    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @NotNull(message = "askQuantity is mandatory")
    @Column(name = "askQuantity")
    private Double askQuantity;

    @NotNull(message = "bid is mandatory")
    private Double bid;

    @NotNull(message = "ask is mandatory")
    private Double ask;

    @NotEmpty(message = "benchmark is mandatory")
    private String benchmark;

    @NotNull(message = "bidListDate is mandatory")
    @Column(name = "bidListDate")
    private Timestamp bidListDate;

    @NotEmpty(message = "commentary is mandatory")
    private String commentary;

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
    private Timestamp creationDate;

    @NotEmpty(message = "revisionName is mandatory")
    @Column(name = "revisionName")
    private String revisionName;

    @NotNull(message = "revisionDate is mandatory")
    @Column(name = "revisionDate")
    private Timestamp revisionDate;

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
