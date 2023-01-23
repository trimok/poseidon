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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CurveId")
    @NotNull(message = "curveId is mandatory")
    @Min(value = 0, message = "the value must be positive")
    @Max(value = 127, message = "the value must be less or equal than 127")
    private Integer curveId;

    @NotNull(message = "asOfDate is mandatory")
    @Column(name = "asOfDate")
    private Timestamp asOfDate = Timestamp.from(Instant.now());

    @NotNull(message = "term is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    private Double term;

    @Column(name = "`value`")
    @NotNull(message = "value is mandatory")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    @Min(value = 0, message = "the value must be positive")
    private Double value;

    @Column(name = "creationDate")
    @NotNull(message = "creationDate is mandatory")
    private Timestamp creationDate = Timestamp.from(Instant.now());
}
