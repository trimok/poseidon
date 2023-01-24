package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Trade;

/**
 * Interface TradeRepository
 * 
 * @author trimok
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
