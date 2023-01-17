package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

public interface IRatingService {

    List<Rating> findAllRatings();

    Rating addRating(Rating rating);

    Rating findRatingById(Integer id);

    Rating updateRating(Rating rating);

    boolean deleteRating(Integer id);

}