package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

/**
 * The interface ITradeServic
 * 
 * @author trimok
 */
public interface ITradeService {

    /**
     * findAllTrades
     * 
     * @return : the list of all the Trade
     */
    List<Trade> findAllTrades();

    /**
     * addTrade
     * 
     * @param trade : the Trade object to be added
     * @return : the added Trade object
     */
    Trade addTrade(Trade trade);

    /**
     * findTradeById
     * 
     * @param id : the id of the Trade object to be found
     * @return : the found Trade object
     */
    Trade findTradeById(Integer id);

    /**
     * updateTrade
     * 
     * @param trade : the updated Trade object
     * @return : the updated Trade object in the databse
     */
    Trade updateTrade(Trade trade);

    /**
     * deleteTrade
     * 
     * @param id : the id of the Trade object to be deleted
     * @return : true if deleted, false if not
     */
    boolean deleteTrade(Integer id);

    /**
     * deleteAllTrade
     */
    void deleteAllTrade();

}