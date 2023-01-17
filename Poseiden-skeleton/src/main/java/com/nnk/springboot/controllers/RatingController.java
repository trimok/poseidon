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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;

import jakarta.validation.Valid;

@Controller
public class RatingController {
    // TODO: Inject Rating service

    @Autowired
    private IRatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
	List<Rating> ratings = ratingService.findAllRatings();
	model.addAttribute("ratings", ratings);

	return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {

	return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
	// TODO: check data valid and save to db, after saving return Rating list
	if (result.hasErrors()) {
	    return "rating/add";
	} else {
	    ratingService.addRating(rating);
	    model.addAttribute("ratings", ratingService.findAllRatings());
	    return "redirect:/rating/list";
	}
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	// TODO: get Rating by Id and to model then show to the form
	Rating rating = ratingService.findRatingById(id);
	if (rating == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	} else {
	    model.addAttribute("rating", rating);
	}
	return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
	    BindingResult result, Model model) {
	// TODO: check required fields, if valid call service to update Rating and
	// return Rating list
	if (result.hasErrors()) {
	    return "rating/update";
	} else {
	    ratingService.updateRating(rating);
	    model.addAttribute("ratings", ratingService.findAllRatings());
	    return "redirect:/rating/list";
	}
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
	// TODO: Find Rating by Id and delete the Rating, return to Rating list
	boolean ok = ratingService.deleteRating(id);
	if (!ok) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}
	model.addAttribute("ratings", ratingService.findAllRatings());
	return "redirect:/rating/list";
    }
}
