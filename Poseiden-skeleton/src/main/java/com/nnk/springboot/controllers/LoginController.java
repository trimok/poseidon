package com.nnk.springboot.controllers;

import static com.nnk.springboot.constants.Constants.AUTHORITY_ADMIN;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.CustomUserDetails;
import com.nnk.springboot.services.ILoginService;

/**
 * Controller class for login operations
 * 
 * @author trimok
 */
@Controller
@SessionAttributes(value = { "user" })
public class LoginController {

    /**
     * The login service
     */
    @Autowired
    private ILoginService loginService;

    /**
     * Management authenticated users, home page, session User object
     * 
     * @param principal : the Principal object
     * @param model     : the model
     * @return : user/home or admin/home page if OK, redirect login if KO
     */
    @GetMapping("/*")
    public String homeFromUserDetails(Principal principal, Model model) {
	if (principal == null) {
	    return "redirect:/login";
	}
	CustomUserDetails userDetails = loginService.getUserDetailsFromPrincipal(principal);
	if (userDetails == null || userDetails.getUser() == null) {
	    return "redirect:/login";
	}

	model.addAttribute("user", userDetails.getUser());

	if (userDetails.hasAuthority(AUTHORITY_ADMIN)) {
	    return "redirect:/admin/home";
	} else {
	    return "redirect:/user/home";
	}
    }

    /**
     * Redirection user/home page
     * 
     * @return user/home page
     */
    @GetMapping("/user/home")
    public String userHome() {
	return "user/home";
    }

    /**
     * Redirection admin/home page
     * 
     * @return admin/home page
     */
    @GetMapping("/admin/home")
    public String adminHome() {
	return "admin/home";
    }

    /**
     * Redirection login page
     * 
     * @return login
     */
    @GetMapping("/login")
    public ModelAndView login() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("login");
	return mav;
    }

    /**
     * Redirection error page
     * 
     * @return error 403
     */
    @GetMapping("/error")
    public ModelAndView error() {
	ModelAndView mav = new ModelAndView();
	String errorMessage = "You are not authorized for the requested data.";
	mav.addObject("errorMsg", errorMessage);
	mav.setViewName("403");
	return mav;
    }
}
