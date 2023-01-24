package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * the Rating Service
 * 
 * @author trimok
 */
@Service
public class RatingService implements IRatingService {

    /**
     * ratingRepository
     */
    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Constructor
     * 
     * @param ratingRepository : ratingRepository
     */
    @Autowired
    public RatingService(RatingRepository ratingRepository) {
	this.ratingRepository = ratingRepository;
    }

    /**
     * findAllRatings
     */
    @Override
    public List<Rating> findAllRatings() {
	return ratingRepository.findAll();
    }

    /**
     * addRating
     */
    @Override
    public Rating addRating(Rating rating) {
	return ratingRepository.save(rating);
    }

    /**
     * findRatingById
     */
    @Override
    public Rating findRatingById(Integer id) {
	Rating rating = ratingRepository.findById(id).orElse(null);
	return rating;
    }

    /**
     * updateRating
     */
    @Override
    public Rating updateRating(Rating rating) {
	return ratingRepository.save(rating);
    }

    /**
     * deleteRating
     */
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

    /**
     * deleteAllRating
     */
    @Override
    public void deleteAllRating() {
	ratingRepository.deleteAll();
    }
}
