package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
	this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAllRatings() {
	return ratingRepository.findAll();
    }

    @Override
    public Rating addRating(Rating rating) {
	return ratingRepository.save(rating);
    }

    @Override
    public Rating findRatingById(Integer id) {
	Rating rating = ratingRepository.findById(id).orElse(null);
	return rating;
    }

    @Override
    public Rating updateRating(Rating rating) {
	return ratingRepository.save(rating);
    }

    @Override
    public boolean deleteRating(Integer id) {
	Rating rating = ratingRepository.findById(id).orElse(null);

	if (rating != null) {
	    ratingRepository.delete(rating);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public void deleteAllRating() {
	ratingRepository.deleteAll();
    }
}
