package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService implements ITradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<Trade> findAllTrades() {
	return tradeRepository.findAll();
    }

    @Override
    public Trade addTrade(Trade trade) {
	return tradeRepository.save(trade);
    }

    @Override
    public Trade findTradeById(Integer id) {
	Trade trade = tradeRepository.findById(id).orElse(null);
	return trade;
    }

    @Override
    public Trade updateTrade(Trade trade) {
	return tradeRepository.save(trade);
    }

    @Override
    public boolean deleteTrade(Integer id) {
	Trade trade = tradeRepository.findById(id).orElse(null);

	if (trade != null) {
	    tradeRepository.delete(trade);
	    return true;
	} else {
	    return false;
	}
    }
}
