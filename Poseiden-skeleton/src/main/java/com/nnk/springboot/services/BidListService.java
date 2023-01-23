package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService implements IBidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
	this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> findAllBidLists() {
	return bidListRepository.findAll();
    }

    @Override
    public BidList addBidList(BidList bidList) {
	return bidListRepository.save(bidList);
    }

    @Override
    public BidList findBidListById(Integer id) {
	BidList bidList = bidListRepository.findById(id).orElse(null);
	return bidList;
    }

    @Override
    public BidList updateBidList(BidList bidList) {
	return bidListRepository.save(bidList);
    }

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

    @Override
    public void deleteAllBidList() {
	bidListRepository.deleteAll();
    }
}
