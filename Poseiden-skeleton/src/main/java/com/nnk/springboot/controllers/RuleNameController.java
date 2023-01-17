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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;

import jakarta.validation.Valid;

@Controller
public class RuleNameController {
    @Autowired
    private IRuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
	List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
	model.addAttribute("ruleNames", ruleNames);

	return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName) {

	return "ruleName/add";
    }

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
