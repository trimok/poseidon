package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * BidList service
 * 
 * @author trimok
 */
@Service
public class BidListService implements IBidListService {

    /**
     * bidListRepository
     */
    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Constructor
     * 
     * @param bidListRepository : the bidListRepository
     */
    @Autowired
    public BidListService(BidListRepository bidListRepository) {
	this.bidListRepository = bidListRepository;
    }

    /**
     * findAllBidLists
     */
    @Override
    public List<BidList> findAllBidLists() {
	return bidListRepository.findAll();
    }

    /**
     * addBidList
     */
    @Override
    public BidList addBidList(BidList bidList) {
	return bidListRepository.save(bidList);
    }

    /**
     * findBidListById
     */
    @Override
    public BidList findBidListById(Integer id) {
	BidList bidList = bidListRepository.findById(id).orElse(null);
	return bidList;
    }

    /**
     * updateBidList
     */
    @Override
    public BidList updateBidList(BidList bidList) {
	return bidListRepository.save(bidList);
    }

    /**
     * deleteBidList
     */
    @Override
    public boolean deleteBidList(Integer id) {
	BidList bidList = bidListRepository.findById(id).orElse(null);

	if (bidList != null) {
	    bidListRepository.delete(bidList);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * deleteAllBidList
     */
    @Override
    public void deleteAllBidList() {
	bidListRepository.deleteAll();
    }
}
