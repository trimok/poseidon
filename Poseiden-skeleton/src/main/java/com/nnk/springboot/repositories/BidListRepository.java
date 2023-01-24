package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.BidList;

/**
 * Interface BidListRepository
 * 
 * @author trimok
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
