package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@ActiveProfiles("test")
public class BidTests {

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void bidListTest() {
	BidList bid = new BidList(null, "account", "type", 1d, 1d, 1d, 1d, "benchmark", Timestamp.from(Instant.now()),
		"commentary", "security",
		"status", "trader", "book", "creationName", Timestamp.from(Instant.now()), "revisionName",
		Timestamp.from(Instant.now()), "dealName", "dealType",
		"sourceListId", "side");

	// Save
	bid = bidListRepository.save(bid);
	assertNotNull(bid.getId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);

	// Update
	bid.setBidQuantity(20d);
	bid = bidListRepository.save(bid);
	assertEquals(bid.getBidQuantity(), 20d, 20d);

	// Find
	List<BidList> listResult = bidListRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = bid.getId();
	bidListRepository.delete(bid);
	Optional<BidList> bidList = bidListRepository.findById(id);
	assertFalse(bidList.isPresent());
    }

}
