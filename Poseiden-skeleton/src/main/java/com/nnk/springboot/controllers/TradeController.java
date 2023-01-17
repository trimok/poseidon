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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;

import jakarta.validation.Valid;

@Controller
public class TradeController {
    @Autowired
    private ITradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
	List<Trade> trades = tradeService.findAllTrades();
	model.addAttribute("trades", trades);

	return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {

	return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "trade/add";
	} else {
	    tradeService.addTrade(trade);
	    model.addAttribute("trades", tradeService.findAllTrades());
	    return "redirect:/trade/list";
	}
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	Trade trade = tradeService.findTradeById(id);
	if (trade == null) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	} else {
	    model.addAttribute("trade", trade);
	}
	return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "trade/update";
	} else {
	    tradeService.updateTrade(trade);
	    model.addAttribute("trades", tradeService.findAllTrades());
	    return "redirect:/trade/list";
	}
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
	boolean ok = tradeService.deleteTrade(id);
	if (!ok) {
	    throw new IllegalArgumentException("Invalid user Id:" + id);
	}
	model.addAttribute("trades", tradeService.findAllTrades());
	return "redirect:/trade/list";
    }
}