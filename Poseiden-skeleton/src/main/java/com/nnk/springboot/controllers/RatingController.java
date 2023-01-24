package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;

import jakarta.validation.Valid;

/**
 * Controller class for Rating CRUD operations
 * 
 * @author trimok
 */
@Controller
@SessionAttributes(value = { "user" })
public class RatingController {
    /**
     * The rating service
     */
    @Autowired
    private IRatingService ratingService;

    /**
     * Home management : access list
     * 
     * @param model : the model
     * @return : the rating/list page
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
	List<Rating> ratings = ratingService.findAllRatings();
	model.addAttribute("ratings", ratings);

	return "rating/list";
    }

    /**
     * Access add page
     * 
     * @param rating : not used
     * @return : the rating/add page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {

	return "rating/add";
    }

    /**
     * Add operation
     * 
     * @param rating : the rating Object to be added
     * @param result : the BindingResult object, with possible validation errors
     * @param model  : the model
     * @return : redirect /rating/list if OK, rating/add page if KO
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "rating/add";
	} else {
	    ratingService.addRating(rating);
	    model.addAttribute("ratings", ratingService.findAllRatings());
	    return "redirect:/rating/list";
	}
    }

    /**
     * 
     * Access update page
     * 
     * @param id    : the Rating object identifier
     * @param model : the model
     * @return : the rating/update page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	Rating rating = ratingService.findRatingById(id);
	if (rating == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	} else {
	    model.addAttribute("rating", rating);
	}
	return "rating/update";
    }

    /**
     * Update operation
     * 
     * @param id     : the Rating object identifier
     * @param rating : the Rating object
     * @param result : th BindingResult object, with possible errors
     * @param model  : the model
     * @return : redirect /rating/list if Ok, of rating/update page if KO
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "rating/update";
	} else {
	    ratingService.updateRating(rating);
	    model.addAttribute("ratings", ratingService.findAllRatings());
	    return "redirect:/rating/list";
	}
    }

    /**
     * Delete operation
     * 
     * @param id    : the Rating Object identifier
     * @param model : the model
     * @return : redirect /rating/list"
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
	boolean ok = ratingService.deleteRating(id);
	if (!ok) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}
	model.addAttribute("ratings", ratingService.findAllRatings());
	return "redirect:/rating/list";
    }
}
