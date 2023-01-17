package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

public interface ITradeService {

    List<Trade> findAllTrades();

    Trade addTrade(Trade trade);

    Trade findTradeById(Integer id);

    Trade updateTrade(Trade trade);

    boolean deleteTrade(Integer id);

}