package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * the Trade Service
 * 
 * @author trimok
 */
@Service
public class TradeService implements ITradeService {

    /**
     * tradeRepository
     */
    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Constructor
     * 
     * @param tradeRepository : tradeRepository
     */
    @Autowired
    public TradeService(TradeRepository tradeRepository) {
	this.tradeRepository = tradeRepository;
    }

    /**
     * findAllTrades
     */
    @Override
    public List<Trade> findAllTrades() {
	return tradeRepository.findAll();
    }

    /**
     * addTrade
     */
    @Override
    public Trade addTrade(Trade trade) {
	return tradeRepository.save(trade);
    }

    /**
     * findTradeById
     */
    @Override
    public Trade findTradeById(Integer id) {
	Trade trade = tradeRepository.findById(id).orElse(null);
	return trade;
    }

    /**
     *
     */
    @Override
    public Trade updateTrade(Trade trade) {
	return tradeRepository.save(trade);
    }

    /**
     * deleteTrade
     */
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

    /**
     * deleteAllTrade
     */
    @Override
    public void deleteAllTrade() {
	tradeRepository.deleteAll();
    }
}
