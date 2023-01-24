package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;

@ActiveProfiles("test")
@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(value = org.junit.jupiter.api.MethodOrderer.MethodName.class)

public class RepositoryTest {
    private Timestamp now = Timestamp.from(Instant.now());

    @Autowired
    private BidListRepository bidListRepository;
    @Autowired
    private CurvePointRepository curvePointRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private RuleNameRepository ruleNameRepository;
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void beforeAll() {
    }

    @BeforeEach
    public void beforeEach() {

    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    void bidListCRUD() {
	BidList bid = new BidList(null, "account", "type", 1d, 1d, 1d, 1d, "benchmark", now,
		"commentary", "security",
		"status", "trader", "book", "creationName", now, "revisionName",
		now, "dealName", "dealType",
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

    @Test
    public void curvePointCRUD() {
	CurvePoint curvePoint = new CurvePoint(null, 10, now, 10d, 30d, now);

	// Save
	curvePoint = curvePointRepository.save(curvePoint);
	assertNotNull(curvePoint.getId());
	assertTrue(curvePoint.getCurveId() == 10);

	// Update
	curvePoint.setCurveId(20);
	curvePoint = curvePointRepository.save(curvePoint);
	assertTrue(curvePoint.getCurveId() == 20);

	// Find
	List<CurvePoint> listResult = curvePointRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = curvePoint.getId();
	curvePointRepository.delete(curvePoint);
	Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
	assertFalse(curvePointList.isPresent());
    }

    @Test
    public void ratingCRUD() {
	Rating rating = new Rating(null, "Moodys Rating",
		"Sand PRating", "Fitch Rating", 10);

	// Save
	rating = ratingRepository.save(rating);
	assertNotNull(rating.getId());
	assertTrue(rating.getOrderNumber() == 10);

	// Update
	rating.setOrderNumber(20);
	rating = ratingRepository.save(rating);
	assertTrue(rating.getOrderNumber() == 20);

	// Find
	List<Rating> listResult = ratingRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = rating.getId();
	ratingRepository.delete(rating);
	Optional<Rating> ratingList = ratingRepository.findById(id);
	assertFalse(ratingList.isPresent());
    }

    @Test
    public void ruleNameCRUD() {
	RuleName rule = new RuleName(null, "Rule Name",
		"Description", "Json", "Template", "SQL", "SQL Part");

	// Save
	rule = ruleNameRepository.save(rule);
	assertNotNull(rule.getId());
	assertTrue(rule.getName().equals("Rule Name"));

	// Update
	rule.setName("Rule Name Update");
	rule = ruleNameRepository.save(rule);
	assertTrue(rule.getName().equals("Rule Name Update"));

	// Find
	List<RuleName> listResult = ruleNameRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = rule.getId();
	ruleNameRepository.delete(rule);
	Optional<RuleName> ruleList = ruleNameRepository.findById(id);
	assertFalse(ruleList.isPresent());
    }

    @Test
    public void tradeCRUD() {
	Trade trade = new Trade(null, "account", "type", 10.0, 10.0, 10.0, 10.0, "benchmark", now, "security", "status",
		"trader", "book", "creationName", now, "revisionName", now, "dealName", "dealType", "sourceListId",
		"side");

	// Save
	trade = tradeRepository.save(trade);
	assertNotNull(trade.getId());
	assertTrue(trade.getAccount().equals("account"));

	// Update
	trade.setAccount("Trade Account Update");
	trade = tradeRepository.save(trade);
	assertTrue(trade.getAccount().equals("Trade Account Update"));

	// Find
	List<Trade> listResult = tradeRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = trade.getId();
	tradeRepository.delete(trade);
	Optional<Trade> tradeList = tradeRepository.findById(id);
	assertFalse(tradeList.isPresent());
    }

    @Test
    public void userCRUD() {
	User user = new User(null, "guest", "@1GuestGuest", "GUEST", AUTHORITY_USER);

	// Save
	user = userRepository.save(user);
	assertNotNull(user.getId());
	assertTrue(user.getUsername().equals("guest"));

	// Update
	user.setUsername("new guest");
	user = userRepository.save(user);
	assertTrue(user.getUsername().equals("new guest"));

	// Find
	List<User> listResult = userRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = user.getId();
	userRepository.delete(user);
	Optional<User> userList = userRepository.findById(id);
	assertFalse(userList.isPresent());
    }
}
