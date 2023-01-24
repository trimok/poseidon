package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

/**
 * interface IBidListService
 * 
 * @author trimok
 */
public interface IBidListService {

    /**
     * findAllBidLists
     * 
     * @return : the list of all BidList
     */
    List<BidList> findAllBidLists();

    /**
     * addBidList
     * 
     * @param bidList :the bidList object to be added
     * @return : the bidList object in the databse
     */
    BidList addBidList(BidList bidList);

    /**
     * findBidListById
     * 
     * @param id : the id of the bidList to be found
     * @return : the bidList object (or null)
     */
    BidList findBidListById(Integer id);

    /**
     * updateBidList
     * 
     * @param bidList : the bidList object to be updated
     * @return : the bidList object (or null)
     */
    BidList updateBidList(BidList bidList);

    /**
     * deleteBidList
     * 
     * @param id : the id of the bidList object to be deleted
     * @return : true if deleted, false if not deleted
     */
    boolean deleteBidList(Integer id);

    /**
     * deleteAllBidList
     */
    void deleteAllBidList();

}