package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Rating;

/**
 * Interface RatingRepository
 * 
 * @author trimok
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
