package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

/**
 * interface IRatingService
 * 
 * @author trimok
 */
public interface IRatingService {

    /**
     * findAllRatings
     * 
     * @return : the list of all ratings
     */
    List<Rating> findAllRatings();

    /**
     * addRating
     * 
     * @param rating : the rating to be added
     * @return : the rating added
     */
    Rating addRating(Rating rating);

    /**
     * findRatingById
     * 
     * @param id : the id of the rating to be found
     * @return : the rating found
     */
    Rating findRatingById(Integer id);

    /**
     * updateRating
     * 
     * @param rating : the updated rating object
     * @return : the updated rating object in the database
     */
    Rating updateRating(Rating rating);

    /**
     * deleteRating
     * 
     * @param id : the id of the rating to be deleted
     * @return : true if deleted, false if not deleted
     */
    boolean deleteRating(Integer id);

    /**
     * deleteAllRating
     */
    void deleteAllRating();

}