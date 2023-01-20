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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes(value = { "user" })
public class CurvePointController {
    @Autowired
    private ICurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
	List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
	model.addAttribute("curvePoints", curvePoints);

	return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {

	return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "curvePoint/add";
	} else {
	    curvePointService.addCurvePoint(curvePoint);
	    model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
	    return "redirect:/curvePoint/list";
	}
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	CurvePoint curvePoint = curvePointService.findCurvePointById(id);
	if (curvePoint == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	} else {
	    model.addAttribute("curvePoint", curvePoint);
	}
	return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "curvePoint/update";
	} else {
	    curvePointService.updateCurvePoint(curvePoint);
	    model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
	    return "redirect:/curvePoint/list";
	}
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
	boolean ok = curvePointService.deleteCurvePoint(id);
	if (!ok) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}
	model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
	return "redirect:/curvePoint/list";
    }
}
