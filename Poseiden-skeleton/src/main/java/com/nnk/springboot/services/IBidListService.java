package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

public interface IBidListService {

    List<BidList> findAllBidLists();

    BidList addBidList(BidList bidList);

    BidList findBidListById(Integer id);

    BidList updateBidList(BidList bidList);

    boolean deleteBidList(Integer id);

}