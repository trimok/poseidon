package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

import jakarta.validation.Valid;

public interface IRatingService {

    List<Rating> findAllRatings();

    Rating addRating(Rating rating);

    Rating findRatingById(Integer id);

    Rating updateRating(@Valid Rating rating);

    boolean deleteRating(Integer id);

}