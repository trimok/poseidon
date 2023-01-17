package com.nnk.springboot.domain;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    public CurvePoint(Integer curveId, Double term, Double value) {
	this.curveId = curveId;
	this.term = term;
	this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CurveId")
    @NotNull(message = "curveId is mandatory")
    private Integer curveId;

    @NotNull(message = "asOfDate is mandatory")
    @Column(name = "asOfDate")
    private Timestamp asOfDate = Timestamp.from(Instant.now());

    @NotNull(message = "term is mandatory")
    private Double term;

    @NotNull(message = "value is mandatory")
    private Double value;

    @Column(name = "creationDate")
    @NotNull(message = "creationDate is mandatory")
    private Timestamp creationDate = Timestamp.from(Instant.now());
}
