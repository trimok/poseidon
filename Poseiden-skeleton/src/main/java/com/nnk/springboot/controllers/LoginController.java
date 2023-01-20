package com.nnk.springboot.controllers;

import static com.nnk.springboot.constants.Constants.AUTHORITY_ADMIN;
import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.CustomUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.ILoginService;

@Controller
@SessionAttributes(value = { "user" })
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @GetMapping("/*")
    public String afterOauth2OidcLogin(Principal principal, Model model) {
	User user = loginService.getUserFromOauth2OidcPrincipal(principal);
	model.addAttribute("user", user);

	if (principal instanceof UsernamePasswordAuthenticationToken
		|| principal instanceof OAuth2AuthenticationToken) {
	    return "user/home";
	} else {
	    return "/login";
	}
    }

    @GetMapping("/successHandler")
    public String afterStandardLogin(Authentication authentication, Model model) {
	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	User user = userDetails.getUser();
	model.addAttribute("user", user);

	String url = "";
	if (userDetails.hasAuthority(AUTHORITY_ADMIN)) {
	    url = "admin/home";

	} else if (userDetails.hasAuthority(AUTHORITY_USER)) {
	    url = "user/home";
	} else {
	    return "/login";
	}
	return url;
    }

    @GetMapping("/user/home")
    public String userHome() {
	return "user/home";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
	return "admin/home";
    }

    @GetMapping("login")
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
