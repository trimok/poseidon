package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "moodysRating is mandatory")
    @Column(name = "moodysRating")
    private String moodysRating;

    @NotBlank(message = "sandPRating is mandatory")
    @Column(name = "sandPRating")
    private String sandPRating;

    @NotBlank(message = "fitchRating is mandatory")
    @Column(name = "fitchRating")
    private String fitchRating;

    @NotBlank(message = "orderNumber is mandatory")
    @Column(name = "orderNumber")
    private Integer orderNumber;
}
