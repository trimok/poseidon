package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_ADMIN;
import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IBidListService;
import com.nnk.springboot.services.ICurvePointService;
import com.nnk.springboot.services.IRatingService;
import com.nnk.springboot.services.IRuleNameService;
import com.nnk.springboot.services.ITradeService;
import com.nnk.springboot.services.IUserService;

@ContextConfiguration
@WithMockUser(username = "user", authorities = { AUTHORITY_USER })
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(value = org.junit.jupiter.api.MethodOrderer.MethodName.class)
public class IntegrationTest {

    private Timestamp now = Timestamp.from(Instant.now());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    IBidListService bidListService;
    @Autowired
    ICurvePointService curvePointService;
    @Autowired
    IRatingService ratingService;
    @Autowired
    IRuleNameService ruleNameService;
    @Autowired
    ITradeService tradeService;
    @Autowired
    IUserService userService;

    private User userSession;
    private HashMap<String, Object> sessionAttr;

    private BidList bidListDatabase;
    private CurvePoint curvePointDatabase;
    private Rating ratingDatabase;
    private RuleName ruleNameDatabase;
    private Trade tradeDatabase;
    private User userDatabase;

    private BidList bidListDatabaseUpdate;
    private CurvePoint curvePointDatabaseUpdate;
    private Rating ratingDatabaseUpdate;
    private RuleName ruleNameDatabaseUpdate;
    private Trade tradeDatabaseUpdate;
    private User userDatabaseUpdate;

    @BeforeEach
    public void beforeEach() {
	userSession = new User(null, "guest", "password", "GUEST", AUTHORITY_USER);

	sessionAttr = new HashMap<String, Object>();
	sessionAttr.put("user", userSession);

	bidListDatabase = new BidList(null, "account", "type", 1d, 1d, 1d, 1d, "benchmark", now,
		"commentary", "security",
		"status", "trader", "book", "creationName", now, "revisionName",
		now, "dealName", "dealType",
		"sourceListId", "side");

	curvePointDatabase = new CurvePoint(null, 10, now, 10d, 30d, now);

	ratingDatabase = new Rating(null, "Moodys Rating",
		"Sand PRating", "Fitch Rating", 10);

	ruleNameDatabase = new RuleName(null, "Rule Name",
		"Description", "Json", "Template", "SQL", "SQL Part");

	tradeDatabase = new Trade(null, "account", "type", 10.0, 10.0, 10.0, 10.0, "benchmark", now, "security",
		"status",
		"trader", "book", "creationName", now, "revisionName", now, "dealName", "dealType", "sourceListId",
		"side");
	userDatabase = new User(null, "guest", "@1GuestGuest", "GUEST", AUTHORITY_USER);

	bidListDatabaseUpdate = new BidList(null, "account Update", "type", 1d, 1d, 1d, 1d, "benchmark", now,
		"commentary", "security",
		"status", "trader", "book", "creationName", now, "revisionName",
		now, "dealName", "dealType",
		"sourceListId", "side");

	curvePointDatabaseUpdate = new CurvePoint(null, 20, now, 10d, 30d, now);

	ratingDatabaseUpdate = new Rating(null, "Moodys Rating Update",
		"Sand PRating", "Fitch Rating", 10);

	ruleNameDatabaseUpdate = new RuleName(null, "Rule Name Update",
		"Description", "Json", "Template", "SQL", "SQL Part");

	tradeDatabaseUpdate = new Trade(null, "account Update", "type", 10.0, 10.0, 10.0, 10.0, "benchmark", now,
		"security",
		"status",
		"trader", "book", "creationName", now, "revisionName", now, "dealName", "dealType", "sourceListId",
		"side");
	userDatabaseUpdate = new User(null, "guest", "@1GuestGuest", "GUEST UPDATE", AUTHORITY_USER);
    }

    @AfterEach
    public void afterEach() {
	bidListService.deleteAllBidList();
	curvePointService.deleteAllCurvePoint();
	ratingService.deleteAllRating();
	ruleNameService.deleteAllRuleName();
	tradeService.deleteAllTrade();
	userService.deleteAllUser();
    }

    /*************************** BIDLIST ************************************/

    @Test
    public void bidListControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/validate").sessionAttrs(sessionAttr).flashAttr("bidList",
			bidListDatabase))
		.andExpect(redirectedUrl("/bidList/list"));

	List<BidList> bidLists = bidListService.findAllBidLists();
	assertThat(bidLists.size()).isEqualTo(1);

	BidList bidList = bidLists.get(0);
	assertThat(bidList).isEqualTo(bidListDatabase);
    }

    @Test
    public void bidListControllerUpdateBidList() throws Exception {

	BidList bidList = bidListService.addBidList(bidListDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/update/" + bidList.getId()).sessionAttrs(sessionAttr).flashAttr(
			"bidList",
			bidListDatabaseUpdate))
		.andExpect(redirectedUrl("/bidList/list"));

	List<BidList> bidLists = bidListService.findAllBidLists();
	assertThat(bidLists.size()).isEqualTo(1);

	bidList = bidLists.get(0);
	assertThat(bidList).isEqualTo(bidListDatabaseUpdate);
    }

    @Test
    public void bidListControllerDeleteBidList() throws Exception {

	BidList bidList = bidListService.addBidList(bidListDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.get("/bidList/delete/" + bidList.getId()).sessionAttrs(sessionAttr))
		.andExpect(redirectedUrl("/bidList/list"));

	List<BidList> bidLists = bidListService.findAllBidLists();
	assertThat(bidLists.size()).isEqualTo(0);
    }

    /*************************** CURVEPOINT ************************************/

    @Test
    public void curvePointControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/curvePoint/validate").sessionAttrs(sessionAttr).flashAttr("curvePoint",
			curvePointDatabase))
		.andExpect(redirectedUrl("/curvePoint/list"));

	List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
	assertThat(curvePoints.size()).isEqualTo(1);

	CurvePoint curvePoint = curvePoints.get(0);
	assertThat(curvePoint).isEqualTo(curvePointDatabase);
    }

    @Test
    public void curvePointControllerUpdateCurvePoint() throws Exception {

	CurvePoint curvePoint = curvePointService.addCurvePoint(curvePointDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/curvePoint/update/" + curvePoint.getId()).sessionAttrs(sessionAttr)
			.flashAttr(
				"curvePoint",
				curvePointDatabaseUpdate))
		.andExpect(redirectedUrl("/curvePoint/list"));

	List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
	assertThat(curvePoints.size()).isEqualTo(1);

	curvePoint = curvePoints.get(0);
	assertThat(curvePoint).isEqualTo(curvePointDatabaseUpdate);
    }

    @Test
    public void curvePointControllerDeleteCurvePoint() throws Exception {

	CurvePoint curvePoint = curvePointService.addCurvePoint(curvePointDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.get("/curvePoint/delete/" + curvePoint.getId()).sessionAttrs(sessionAttr))
		.andExpect(redirectedUrl("/curvePoint/list"));

	List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
	assertThat(curvePoints.size()).isEqualTo(0);
    }

    /*************************** RATING ************************************/

    @Test
    public void ratingControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/rating/validate").sessionAttrs(sessionAttr).flashAttr("rating",
			ratingDatabase))
		.andExpect(redirectedUrl("/rating/list"));

	List<Rating> ratings = ratingService.findAllRatings();
	assertThat(ratings.size()).isEqualTo(1);

	Rating rating = ratings.get(0);
	assertThat(rating).isEqualTo(ratingDatabase);
    }

    @Test
    public void ratingControllerUpdateRating() throws Exception {

	Rating rating = ratingService.addRating(ratingDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/rating/update/" + rating.getId()).sessionAttrs(sessionAttr)
			.flashAttr(
				"rating",
				ratingDatabaseUpdate))
		.andExpect(redirectedUrl("/rating/list"));

	List<Rating> ratings = ratingService.findAllRatings();
	assertThat(ratings.size()).isEqualTo(1);

	rating = ratings.get(0);
	assertThat(rating).isEqualTo(ratingDatabaseUpdate);
    }

    @Test
    public void ratingControllerDeleteRating() throws Exception {

	Rating rating = ratingService.addRating(ratingDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.get("/rating/delete/" + rating.getId()).sessionAttrs(sessionAttr))
		.andExpect(redirectedUrl("/rating/list"));

	List<Rating> ratings = ratingService.findAllRatings();
	assertThat(ratings.size()).isEqualTo(0);
    }

    /*************************** RULENAME ************************************/

    @Test
    public void ruleNameControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/ruleName/validate").sessionAttrs(sessionAttr).flashAttr("ruleName",
			ruleNameDatabase))
		.andExpect(redirectedUrl("/ruleName/list"));

	List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
	assertThat(ruleNames.size()).isEqualTo(1);

	RuleName ruleName = ruleNames.get(0);
	assertThat(ruleName).isEqualTo(ruleNameDatabase);
    }

    @Test
    public void ruleNameControllerUpdateRuleName() throws Exception {

	RuleName ruleName = ruleNameService.addRuleName(ruleNameDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/ruleName/update/" + ruleName.getId()).sessionAttrs(sessionAttr)
			.flashAttr(
				"ruleName",
				ruleNameDatabaseUpdate))
		.andExpect(redirectedUrl("/ruleName/list"));

	List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
	assertThat(ruleNames.size()).isEqualTo(1);

	ruleName = ruleNames.get(0);
	assertThat(ruleName).isEqualTo(ruleNameDatabaseUpdate);
    }

    @Test
    public void ruleNameControllerDeleteRuleName() throws Exception {

	RuleName ruleName = ruleNameService.addRuleName(ruleNameDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.get("/ruleName/delete/" + ruleName.getId()).sessionAttrs(sessionAttr))
		.andExpect(redirectedUrl("/ruleName/list"));

	List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
	assertThat(ruleNames.size()).isEqualTo(0);
    }

    /*************************** TRADE ************************************/

    @Test
    public void tradeControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/trade/validate").sessionAttrs(sessionAttr).flashAttr("trade",
			tradeDatabase))
		.andExpect(redirectedUrl("/trade/list"));

	List<Trade> trades = tradeService.findAllTrades();
	assertThat(trades.size()).isEqualTo(1);

	Trade trade = trades.get(0);
	assertThat(trade).isEqualTo(tradeDatabase);
    }

    @Test
    public void tradeControllerUpdateTrade() throws Exception {

	Trade trade = tradeService.addTrade(tradeDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/trade/update/" + trade.getId()).sessionAttrs(sessionAttr)
			.flashAttr(
				"trade",
				tradeDatabaseUpdate))
		.andExpect(redirectedUrl("/trade/list"));

	List<Trade> trades = tradeService.findAllTrades();
	assertThat(trades.size()).isEqualTo(1);

	trade = trades.get(0);
	assertThat(trade).isEqualTo(tradeDatabaseUpdate);
    }

    @Test
    public void tradeControllerDeleteTrade() throws Exception {

	Trade trade = tradeService.addTrade(tradeDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.get("/trade/delete/" + trade.getId()).sessionAttrs(sessionAttr))
		.andExpect(redirectedUrl("/trade/list"));

	List<Trade> trades = tradeService.findAllTrades();
	assertThat(trades.size()).isEqualTo(0);
    }

    /*************************** USER ************************************/

    @Test
    @WithMockUser(username = "admin", authorities = { AUTHORITY_ADMIN })
    public void userControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/user/validate").sessionAttrs(sessionAttr).flashAttr("user",
			userDatabase))
		.andExpect(redirectedUrl("/user/list"));

	List<User> users = userService.findAllUsers();
	assertThat(users.size()).isEqualTo(1);

	User user = users.get(0);
	assertThat(user).isEqualTo(userDatabase);
    }

    @Test
    @WithMockUser(username = "admin", authorities = { AUTHORITY_ADMIN })
    public void userControllerUpdateUser() throws Exception {

	User user = userService.addUser(userDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/user/update/" + user.getId()).sessionAttrs(sessionAttr)
			.flashAttr(
				"user",
				userDatabaseUpdate))
		.andExpect(redirectedUrl("/user/list"));

	List<User> users = userService.findAllUsers();
	assertThat(users.size()).isEqualTo(1);

	user = users.get(0);
	assertThat(user).isEqualTo(userDatabaseUpdate);
    }

    @Test
    @WithMockUser(username = "admin", authorities = { AUTHORITY_ADMIN })
    public void userControllerDeleteUser() throws Exception {

	User user = userService.addUser(userDatabase);

	mockMvc.perform(
		MockMvcRequestBuilders.get("/user/delete/" + user.getId()).sessionAttrs(sessionAttr))
		.andExpect(redirectedUrl("/user/list"));

	List<User> users = userService.findAllUsers();
	assertThat(users.size()).isEqualTo(0);
    }
}
