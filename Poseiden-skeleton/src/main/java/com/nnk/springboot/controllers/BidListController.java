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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

import jakarta.validation.Valid;

/**
 * Controller for BidList CRUD operations
 * 
 * @author trimok
 */
@Controller
@SessionAttributes(value = { "user" })
public class BidListController {
    /**
     * The bidListService
     */
    @Autowired
    private IBidListService bidListService;

    /**
     * Home management
     * 
     * @param model : the model
     * @return : the bidList/list page
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
	List<BidList> bidLists = bidListService.findAllBidLists();
	model.addAttribute("bidLists", bidLists);

	return "bidList/list";
    }

    /**
     * Add access
     * 
     * @param bidList : not used
     * @return : the bidList/add page
     */
    @GetMapping("/bidList/add")
    public String addBidListForm(BidList bidList) {

	return "bidList/add";
    }

    /**
     * Add CRUD operation
     * 
     * @param bidList : a valid BidList object
     * @param result  : the result object with possible errors
     * @param model   : the model
     * @return : /bidList/list" page is OK, bidList/add is KO
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "bidList/add";
	} else {
	    bidListService.addBidList(bidList);
	    model.addAttribute("bidLists", bidListService.findAllBidLists());
	    return "redirect:/bidList/list";
	}
    }

    /**
     * Update access
     * 
     * @param id    : the BidList identifier
     * @param model : the model
     * @return : bidList/update page
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	BidList bidList = bidListService.findBidListById(id);
	if (bidList == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	} else {
	    model.addAttribute("bidList", bidList);
	}
	return "bidList/update";
    }

    /**
     * Update CRUD operation
     * 
     * @param id      : the BidList object identifier
     * @param bidList : the BidList object
     * @param result  : the BindingResult with possible errors
     * @param model   : the model
     * @return : the /bidList/list page if OK, the bidList/update page if KO
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBidList(@PathVariable("id") Integer id, @Valid BidList bidList,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "bidList/update";
	} else {
	    bidListService.updateBidList(bidList);
	    model.addAttribute("bidLists", bidListService.findAllBidLists());
	    return "redirect:/bidList/list";
	}
    }

    /**
     * Delete CRUD operation
     * 
     * @param id    : the BidList identifier
     * @param model : the model
     * @return : the /bidList/list page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBidList(@PathVariable("id") Integer id, Model model) {
	boolean ok = bidListService.deleteBidList(id);
	if (!ok) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}
	model.addAttribute("bidLists", bidListService.findAllBidLists());
	return "redirect:/bidList/list";
    }
}
