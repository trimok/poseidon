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

/**
 * Controller class for CurvePoint CRUD operations
 * 
 * @author trimok
 */
@Controller
@SessionAttributes(value = { "user" })
public class CurvePointController {
    /**
     * the curvePoint Service
     */
    @Autowired
    private ICurvePointService curvePointService;

    /**
     * Home management
     * 
     * @param model : the model
     * @return : the curvePoint/list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
	List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
	model.addAttribute("curvePoints", curvePoints);

	return "curvePoint/list";
    }

    /**
     * Acess add page
     * 
     * @param curvePoint : not used
     * @return : the curvePoint/add page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {

	return "curvePoint/add";
    }

    /**
     * Add CRUD operation
     * 
     * @param curvePoint : the CurvePoint object
     * @param result     : the BindingResult object, with possible errors
     * @param model      : the model
     * @return : the /curvePoint/list page if OK, the curvePoint/add page if KO
     */
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

    /**
     * Access update page
     * 
     * @param id    : the CurvePoint object identifier
     * @param model : the model
     * @return : the curvePoint/update page
     */
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

    /**
     * Update CRUD operation
     * 
     * @param id         : the CurvePoint object identifier
     * @param curvePoint : the CurvePoint object
     * @param result     : the BindingResult object with possible errors
     * @param model      : the model
     * @return : redirection /curvePoint/list if OK, curvePoint/update if KO
     */
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

    /**
     * Delete operation
     * 
     * @param id    : the CurvePoint object identifier
     * @param model : the model
     * @return : redirection /curvePoint/list
     */
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
