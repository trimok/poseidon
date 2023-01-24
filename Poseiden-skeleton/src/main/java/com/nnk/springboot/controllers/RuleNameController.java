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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;

import jakarta.validation.Valid;

/**
 * Controller class for the RuleName CRUD operations
 * 
 * @author trimok
 */
@Controller
@SessionAttributes(value = { "user" })
public class RuleNameController {
    /**
     * The ruleName service
     */
    @Autowired
    private IRuleNameService ruleNameService;

    /**
     * Access list page
     * 
     * @param model : the model
     * @return : the ruleName/list page
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
	List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
	model.addAttribute("ruleNames", ruleNames);

	return "ruleName/list";
    }

    /**
     * Access add page
     * 
     * @param ruleName : not used
     * @return : the ruleName/add page
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName) {

	return "ruleName/add";
    }

    /**
     * Add operation
     * 
     * @param ruleName : the valid RuleName object
     * @param result   : the BindingResult object, with possible errors
     * @param model    : the model
     * @return : redirect:/ruleName/list id OK, ruleName/add page if KO
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "ruleName/add";
	} else {
	    ruleNameService.addRuleName(ruleName);
	    model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
	    return "redirect:/ruleName/list";
	}
    }

    /**
     * Access update page
     * 
     * @param id    : the RuleName object identifier
     * @param model : the model
     * @return : the ruleName/update page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	RuleName ruleName = ruleNameService.findRuleNameById(id);
	if (ruleName == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	} else {
	    model.addAttribute("ruleName", ruleName);
	}
	return "ruleName/update";
    }

    /**
     * Update operation
     * 
     * @param id       : the RuleName object identifier
     * @param ruleName : the RuleName object
     * @param result   : the BindingResult object
     * @param model    : the model
     * @return : redirect:/ruleName/list if OK, ruleName/update page if KO
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "ruleName/update";
	} else {
	    ruleNameService.updateRuleName(ruleName);
	    model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
	    return "redirect:/ruleName/list";
	}
    }

    /**
     * Delete operation
     * 
     * @param id    : the RuleName identifier
     * @param model : the model
     * @return : redirect:/ruleName/list
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
	boolean ok = ruleNameService.deleteRuleName(id);
	if (!ok) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}
	model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
	return "redirect:/ruleName/list";
    }
}
