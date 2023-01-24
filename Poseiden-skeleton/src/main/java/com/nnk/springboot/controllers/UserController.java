package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

import jakarta.validation.Valid;

/**
 * The controller class for User CRUD operations
 * 
 * @author trimok
 */
@Controller
@SessionAttributes(value = { "user" })
public class UserController {
    /**
     * The user service
     */
    @Autowired
    private IUserService userService;

    /**
     * Access user list
     * 
     * @param model : the model
     * @return : the user/list page
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
	model.addAttribute("users", userService.findAllUsers());
	return "user/list";
    }

    /**
     * Access add page
     * 
     * @param bid : not used
     * @return : the user/add page
     */
    @GetMapping("/user/add")
    public String addUser(User bid) {
	return "user/add";
    }

    /**
     * Add operation
     * 
     * @param user   : the User object to be added
     * @param result : the BindingResult object
     * @param model  : the model
     * @return : the user/add page if KO, redirect redirect:/user/list" if OK
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
	if (!result.hasErrors()) {
	    userService.addUser(user);
	    model.addAttribute("users", userService.findAllUsers());
	    return "redirect:/user/list";
	}
	return "user/add";
    }

    /**
     * Access update page
     * 
     * @param id    : the User object identifier
     * @param model : the model
     * @return : the user/update page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	User user = userService.findUserById(id);
	if (user == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}

	user.setPassword("");
	model.addAttribute("user", user);
	return "user/update";
    }

    /**
     * Update operation
     * 
     * @param id     : the User object identifier
     * @param user   : the User object
     * @param result : the BindingResult
     * @param model  : the model
     * @return : redirect:/user/list if OK, user/update if KO
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "user/update";
	}

	user.setId(id);
	userService.updateUser(user);
	model.addAttribute("users", userService.findAllUsers());
	return "redirect:/user/list";
    }

    /**
     * Delete operation
     * 
     * @param id    : the User identifier
     * @param model : the model
     * @return : redirect:/user/list
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
	User user = userService.findUserById(id);
	if (user == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}

	userService.deleteUser(id);
	model.addAttribute("users", userService.findAllUsers());
	return "redirect:/user/list";
    }
}
