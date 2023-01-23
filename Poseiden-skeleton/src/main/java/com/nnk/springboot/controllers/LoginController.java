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

@Controller
@SessionAttributes(value = { "user" })
public class LoginController {

    @Autowired
    private ILoginService loginService;

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

    @GetMapping("/user/home")
    public String userHome() {
	return "user/home";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
	return "admin/home";
    }

    @GetMapping("/login")
    public ModelAndView login() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("login");
	return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {
	ModelAndView mav = new ModelAndView();
	String errorMessage = "You are not authorized for the requested data.";
	mav.addObject("errorMsg", errorMessage);
	mav.setViewName("403");
	return mav;
    }
}
