package com.nnk.springboot.domain;

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
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotEmpty(message = "moodysRating is mandatory")
    @Column(name = "moodysRating")
    private String moodysRating;

    @NotEmpty(message = "sandPRating is mandatory")
    @Column(name = "sandPRating")
    private String sandPRating;

    @NotEmpty(message = "fitchRating is mandatory")
    @Column(name = "fitchRating")
    private String fitchRating;

    @NotNull(message = "orderNumber is mandatory")
    @Column(name = "orderNumber")
    private Integer orderNumber;
}
