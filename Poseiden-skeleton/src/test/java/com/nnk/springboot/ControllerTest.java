package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_ADMIN;
import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IBidListService;
import com.nnk.springboot.services.ICurvePointService;
import com.nnk.springboot.services.ILoginService;
import com.nnk.springboot.services.IRatingService;
import com.nnk.springboot.services.IRuleNameService;
import com.nnk.springboot.services.ITradeService;
import com.nnk.springboot.services.IUserService;

import jakarta.servlet.http.HttpSession;

@WebMvcTest({ LoginController.class, BidListController.class, CurvePointController.class, RatingController.class,
	RuleNameController.class, TradeController.class, UserController.class })
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(value = org.junit.jupiter.api.MethodOrderer.MethodName.class)
public class ControllerTest {

    private Timestamp now = Timestamp.from(Instant.now());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IBidListService bidListService;

    @MockBean
    ICurvePointService curvePointService;

    @MockBean
    IRatingService ratingService;

    @MockBean
    IRuleNameService ruleNameService;

    @MockBean
    ITradeService tradeService;

    @MockBean
    IUserService userService;

    @MockBean
    ILoginService loginService;

    @Mock
    HttpSession mockHttpSession;

    private BidList bidListDatabase;
    private CurvePoint curvePointDatabase;
    private Rating ratingDatabase;
    private RuleName ruleNameDatabase;
    private Trade tradeDatabase;
    private User userDatabase;

    private HashMap<String, Object> sessionAttr;
    private User userSession;
    private User userAdminSession;
    private CustomUserDetails userDetails;
    private CustomUserDetails userAdminDetails;

    @BeforeEach
    public void beforeEach() {

	userSession = new User(null, "guest", "password", "GUEST", AUTHORITY_USER);
	userDetails = new CustomUserDetails(userSession, AUTHORITY_USER);

	userAdminSession = new User(null, "admin", "admin", "ADMIN", AUTHORITY_ADMIN);
	userAdminDetails = new CustomUserDetails(userAdminSession, AUTHORITY_ADMIN);

	sessionAttr = new HashMap<String, Object>();

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
    }

    @AfterAll
    public void afterAll() {
    }

    /************ BIDLIST CONTROLLER **********/

    @Test
    public void bidListControllerHome() throws Exception {

	sessionAttr.put("user", userSession);

	List<BidList> bidLists = new ArrayList<BidList>();
	bidLists.add(bidListDatabase);

	when(bidListService.findAllBidLists()).thenReturn(bidLists);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/bidList/list").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("bidList/list"))
		.andExpect(model().attributeExists("bidLists"));

	verify(bidListService, times(1)).findAllBidLists();
    }

    @Test
    public void bidListControllerAddBidListForm() throws Exception {
	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/bidList/add").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("bidList/add"));
    }

    @Test
    public void bidListControllerValidate() throws Exception {
	sessionAttr.put("user", userSession);

	when(bidListService.addBidList(any(BidList.class))).thenReturn(bidListDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/bidList/validate").sessionAttrs(sessionAttr).flashAttr(
			"bidList",
			bidListDatabase))
		.andExpect(redirectedUrl("/bidList/list"));

	verify(bidListService, times(1)).addBidList(any(BidList.class));
    }

    @Test
    public void bidListControllerShowUpdateForm() throws Exception {
	sessionAttr.put("user", userSession);

	when(bidListService.findBidListById(any(Integer.class))).thenReturn(bidListDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/bidList/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(status().is(200)).andExpect(view().name("bidList/update"))
		.andExpect(model().attributeExists("bidList"));

	verify(bidListService, times(1)).findBidListById(any(Integer.class));
    }

    @Test
    public void bidListControllerUpdateBidList() throws Exception {
	sessionAttr.put("user", userSession);

	List<BidList> bidLists = new ArrayList<BidList>();
	bidLists.add(bidListDatabase);

	when(bidListService.findAllBidLists()).thenReturn(bidLists);
	when(bidListService.updateBidList(any(BidList.class))).thenReturn(bidListDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/bidList/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1).flashAttr(
				"bidList",
				bidListDatabase))
		.andExpect(redirectedUrl("/bidList/list"));

	verify(bidListService, times(1)).updateBidList(any(BidList.class));
	verify(bidListService, times(1)).findAllBidLists();
    }

    @Test
    public void bidListControllerDeleteBidList() throws Exception {
	sessionAttr.put("user", userSession);

	List<BidList> bidLists = new ArrayList<BidList>();
	bidLists.add(bidListDatabase);

	when(bidListService.findAllBidLists()).thenReturn(bidLists);
	when(bidListService.deleteBidList(any(Integer.class))).thenReturn(true);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/bidList/delete/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(redirectedUrl("/bidList/list"));

	verify(bidListService, times(1)).deleteBidList(any(Integer.class));
	verify(bidListService, times(1)).findAllBidLists();
    }

    /************ CURVEPOINT CONTROLLER **********/

    @Test
    public void curvePointControllerHome() throws Exception {

	sessionAttr.put("user", userSession);

	List<CurvePoint> curvePoints = new ArrayList<CurvePoint>();
	curvePoints.add(curvePointDatabase);

	when(curvePointService.findAllCurvePoints()).thenReturn(curvePoints);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/curvePoint/list").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("curvePoint/list"))
		.andExpect(model().attributeExists("curvePoints"));

	verify(curvePointService, times(1)).findAllCurvePoints();
    }

    @Test
    public void curvePointControllerAddCurvePointForm() throws Exception {
	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/curvePoint/add").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void curvePointControllerValidate() throws Exception {
	sessionAttr.put("user", userSession);

	when(curvePointService.addCurvePoint(any(CurvePoint.class))).thenReturn(curvePointDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/curvePoint/validate").sessionAttrs(sessionAttr).flashAttr(
			"curvePoint",
			curvePointDatabase))
		.andExpect(redirectedUrl("/curvePoint/list"));

	verify(curvePointService, times(1)).addCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void curvePointControllerShowUpdateForm() throws Exception {
	sessionAttr.put("user", userSession);

	when(curvePointService.findCurvePointById(any(Integer.class))).thenReturn(curvePointDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/curvePoint/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(status().is(200)).andExpect(view().name("curvePoint/update"))
		.andExpect(model().attributeExists("curvePoint"));

	verify(curvePointService, times(1)).findCurvePointById(any(Integer.class));
    }

    @Test
    public void curvePointControllerUpdateCurvePoint() throws Exception {
	sessionAttr.put("user", userSession);

	List<CurvePoint> curvePoints = new ArrayList<CurvePoint>();
	curvePoints.add(curvePointDatabase);

	when(curvePointService.findAllCurvePoints()).thenReturn(curvePoints);
	when(curvePointService.updateCurvePoint(any(CurvePoint.class))).thenReturn(curvePointDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/curvePoint/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1).flashAttr(
				"curvePoint",
				curvePointDatabase))
		.andExpect(redirectedUrl("/curvePoint/list"));

	verify(curvePointService, times(1)).updateCurvePoint(any(CurvePoint.class));
	verify(curvePointService, times(1)).findAllCurvePoints();
    }

    @Test
    public void curvePointControllerDeleteCurvePoint() throws Exception {
	sessionAttr.put("user", userSession);

	List<CurvePoint> curvePoints = new ArrayList<CurvePoint>();
	curvePoints.add(curvePointDatabase);

	when(curvePointService.findAllCurvePoints()).thenReturn(curvePoints);
	when(curvePointService.deleteCurvePoint(any(Integer.class))).thenReturn(true);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(redirectedUrl("/curvePoint/list"));

	verify(curvePointService, times(1)).deleteCurvePoint(any(Integer.class));
	verify(curvePointService, times(1)).findAllCurvePoints();
    }

    /************ RATING CONTROLLER **********/

    @Test
    public void ratingControllerHome() throws Exception {

	sessionAttr.put("user", userSession);

	List<Rating> ratings = new ArrayList<Rating>();
	ratings.add(ratingDatabase);

	when(ratingService.findAllRatings()).thenReturn(ratings);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/rating/list").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("rating/list"))
		.andExpect(model().attributeExists("ratings"));

	verify(ratingService, times(1)).findAllRatings();
    }

    @Test
    public void ratingControllerAddRatingForm() throws Exception {
	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/rating/add").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("rating/add"));
    }

    @Test
    public void ratingControllerValidate() throws Exception {
	sessionAttr.put("user", userSession);

	when(ratingService.addRating(any(Rating.class))).thenReturn(ratingDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/rating/validate").sessionAttrs(sessionAttr).flashAttr(
			"rating",
			ratingDatabase))
		.andExpect(redirectedUrl("/rating/list"));

	verify(ratingService, times(1)).addRating(any(Rating.class));
    }

    @Test
    public void ratingControllerShowUpdateForm() throws Exception {
	sessionAttr.put("user", userSession);

	when(ratingService.findRatingById(any(Integer.class))).thenReturn(ratingDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/rating/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(status().is(200)).andExpect(view().name("rating/update"))
		.andExpect(model().attributeExists("rating"));

	verify(ratingService, times(1)).findRatingById(any(Integer.class));
    }

    @Test
    public void ratingControllerUpdateRating() throws Exception {
	sessionAttr.put("user", userSession);

	List<Rating> ratings = new ArrayList<Rating>();
	ratings.add(ratingDatabase);

	when(ratingService.findAllRatings()).thenReturn(ratings);
	when(ratingService.updateRating(any(Rating.class))).thenReturn(ratingDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/rating/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1).flashAttr(
				"rating",
				ratingDatabase))
		.andExpect(redirectedUrl("/rating/list"));

	verify(ratingService, times(1)).updateRating(any(Rating.class));
	verify(ratingService, times(1)).findAllRatings();
    }

    @Test
    public void ratingControllerDeleteRating() throws Exception {
	sessionAttr.put("user", userSession);

	List<Rating> ratings = new ArrayList<Rating>();
	ratings.add(ratingDatabase);

	when(ratingService.findAllRatings()).thenReturn(ratings);
	when(ratingService.deleteRating(any(Integer.class))).thenReturn(true);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/rating/delete/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(redirectedUrl("/rating/list"));

	verify(ratingService, times(1)).deleteRating(any(Integer.class));
	verify(ratingService, times(1)).findAllRatings();
    }

    /************ RULENAME CONTROLLER **********/

    @Test
    public void ruleNameControllerHome() throws Exception {

	sessionAttr.put("user", userSession);

	List<RuleName> ruleNames = new ArrayList<RuleName>();
	ruleNames.add(ruleNameDatabase);

	when(ruleNameService.findAllRuleNames()).thenReturn(ruleNames);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/ruleName/list").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("ruleName/list"))
		.andExpect(model().attributeExists("ruleNames"));

	verify(ruleNameService, times(1)).findAllRuleNames();
    }

    @Test
    public void ruleNameControllerAddRuleNameForm() throws Exception {
	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/ruleName/add").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("ruleName/add"));
    }

    @Test
    public void ruleNameControllerValidate() throws Exception {
	sessionAttr.put("user", userSession);

	when(ruleNameService.addRuleName(any(RuleName.class))).thenReturn(ruleNameDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/ruleName/validate").sessionAttrs(sessionAttr).flashAttr(
			"ruleName",
			ruleNameDatabase))
		.andExpect(redirectedUrl("/ruleName/list"));

	verify(ruleNameService, times(1)).addRuleName(any(RuleName.class));
    }

    @Test
    public void ruleNameControllerShowUpdateForm() throws Exception {
	sessionAttr.put("user", userSession);

	when(ruleNameService.findRuleNameById(any(Integer.class))).thenReturn(ruleNameDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/ruleName/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(status().is(200)).andExpect(view().name("ruleName/update"))
		.andExpect(model().attributeExists("ruleName"));

	verify(ruleNameService, times(1)).findRuleNameById(any(Integer.class));
    }

    @Test
    public void ruleNameControllerUpdateRuleName() throws Exception {
	sessionAttr.put("user", userSession);

	List<RuleName> ruleNames = new ArrayList<RuleName>();
	ruleNames.add(ruleNameDatabase);

	when(ruleNameService.findAllRuleNames()).thenReturn(ruleNames);
	when(ruleNameService.updateRuleName(any(RuleName.class))).thenReturn(ruleNameDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/ruleName/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1).flashAttr(
				"ruleName",
				ruleNameDatabase))
		.andExpect(redirectedUrl("/ruleName/list"));

	verify(ruleNameService, times(1)).updateRuleName(any(RuleName.class));
	verify(ruleNameService, times(1)).findAllRuleNames();
    }

    @Test
    public void ruleNameControllerDeleteRuleName() throws Exception {
	sessionAttr.put("user", userSession);

	List<RuleName> ruleNames = new ArrayList<RuleName>();
	ruleNames.add(ruleNameDatabase);

	when(ruleNameService.findAllRuleNames()).thenReturn(ruleNames);
	when(ruleNameService.deleteRuleName(any(Integer.class))).thenReturn(true);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/ruleName/delete/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(redirectedUrl("/ruleName/list"));

	verify(ruleNameService, times(1)).deleteRuleName(any(Integer.class));
	verify(ruleNameService, times(1)).findAllRuleNames();
    }

    /************ TRADE CONTROLLER **********/

    @Test
    public void tradeControllerHome() throws Exception {

	sessionAttr.put("user", userSession);

	List<Trade> trades = new ArrayList<Trade>();
	trades.add(tradeDatabase);

	when(tradeService.findAllTrades()).thenReturn(trades);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/trade/list").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("trade/list"))
		.andExpect(model().attributeExists("trades"));

	verify(tradeService, times(1)).findAllTrades();
    }

    @Test
    public void tradeControllerAddTradeForm() throws Exception {
	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/trade/add").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("trade/add"));
    }

    @Test
    public void tradeControllerValidate() throws Exception {
	sessionAttr.put("user", userSession);

	when(tradeService.addTrade(any(Trade.class))).thenReturn(tradeDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/trade/validate").sessionAttrs(sessionAttr).flashAttr(
			"trade",
			tradeDatabase))
		.andExpect(redirectedUrl("/trade/list"));

	verify(tradeService, times(1)).addTrade(any(Trade.class));
    }

    @Test
    public void tradeControllerShowUpdateForm() throws Exception {
	sessionAttr.put("user", userSession);

	when(tradeService.findTradeById(any(Integer.class))).thenReturn(tradeDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/trade/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(status().is(200)).andExpect(view().name("trade/update"))
		.andExpect(model().attributeExists("trade"));

	verify(tradeService, times(1)).findTradeById(any(Integer.class));
    }

    @Test
    public void tradeControllerUpdateTrade() throws Exception {
	sessionAttr.put("user", userSession);

	List<Trade> trades = new ArrayList<Trade>();
	trades.add(tradeDatabase);

	when(tradeService.findAllTrades()).thenReturn(trades);
	when(tradeService.updateTrade(any(Trade.class))).thenReturn(tradeDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/trade/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1).flashAttr(
				"trade",
				tradeDatabase))
		.andExpect(redirectedUrl("/trade/list"));

	verify(tradeService, times(1)).updateTrade(any(Trade.class));
	verify(tradeService, times(1)).findAllTrades();
    }

    @Test
    public void tradeControllerDeleteTrade() throws Exception {
	sessionAttr.put("user", userSession);

	List<Trade> trades = new ArrayList<Trade>();
	trades.add(tradeDatabase);

	when(tradeService.findAllTrades()).thenReturn(trades);
	when(tradeService.deleteTrade(any(Integer.class))).thenReturn(true);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/trade/delete/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(redirectedUrl("/trade/list"));

	verify(tradeService, times(1)).deleteTrade(any(Integer.class));
	verify(tradeService, times(1)).findAllTrades();
    }

    /************ USER CONTROLLER **********/

    @Test
    public void userControllerHome() throws Exception {

	sessionAttr.put("user", userSession);

	List<User> users = new ArrayList<User>();
	users.add(userDatabase);

	when(userService.findAllUsers()).thenReturn(users);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/user/list").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("user/list"))
		.andExpect(model().attributeExists("users"));

	verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void userControllerAddUserForm() throws Exception {
	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/user/add").sessionAttrs(sessionAttr))
		.andExpect(status().is(200)).andExpect(view().name("user/add"));
    }

    @Test
    public void userControllerValidate() throws Exception {
	sessionAttr.put("user", userSession);

	when(userService.addUser(any(User.class))).thenReturn(userDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/user/validate").sessionAttrs(sessionAttr).flashAttr(
			"user",
			userDatabase))
		.andExpect(redirectedUrl("/user/list"));

	verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    public void userControllerShowUpdateForm() throws Exception {
	sessionAttr.put("user", userSession);

	when(userService.findUserById(any(Integer.class))).thenReturn(userDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/user/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(status().is(200)).andExpect(view().name("user/update"))
		.andExpect(model().attributeExists("user"));

	verify(userService, times(1)).findUserById(any(Integer.class));
    }

    @Test
    public void userControllerUpdateUser() throws Exception {
	sessionAttr.put("user", userSession);

	List<User> users = new ArrayList<User>();
	users.add(userDatabase);

	when(userService.findAllUsers()).thenReturn(users);
	when(userService.updateUser(any(User.class))).thenReturn(userDatabase);

	mockMvc
		.perform(MockMvcRequestBuilders.post("/user/update/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1).flashAttr(
				"user",
				userDatabase))
		.andExpect(redirectedUrl("/user/list"));

	verify(userService, times(1)).updateUser(any(User.class));
	verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void userControllerDeleteUser() throws Exception {
	sessionAttr.put("user", userSession);

	List<User> users = new ArrayList<User>();
	users.add(userDatabase);

	when(userService.findUserById(any(Integer.class))).thenReturn(userDatabase);
	when(userService.deleteUser(any(Integer.class))).thenReturn(true);
	when(userService.findAllUsers()).thenReturn(users);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/user/delete/1").sessionAttrs(sessionAttr).flashAttr(
			"id",
			1))
		.andExpect(redirectedUrl("/user/list"));

	verify(userService, times(1)).findUserById(any(Integer.class));
	verify(userService, times(1)).deleteUser(any(Integer.class));
	verify(userService, times(1)).findAllUsers();

    }

    /************************* LOGIN CONTROLLER ******************************/
    @Test
    public void loginControllerHomeFromUserDetailsStandardUser() throws Exception {

	Principal principal = new UsernamePasswordAuthenticationToken(userDatabase, "password");

	sessionAttr.put("user", userSession);

	when(loginService.getUserDetailsFromPrincipal(any(Principal.class))).thenReturn(userDetails);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/").sessionAttrs(sessionAttr).principal(principal))
		.andExpect(redirectedUrl("/user/home"));

	verify(loginService, times(1)).getUserDetailsFromPrincipal(any(Principal.class));
    }

    @Test
    public void loginControllerHomeFromUserDetailsAdminUser() throws Exception {

	Principal principal = new UsernamePasswordAuthenticationToken(userDatabase, "password");

	sessionAttr.put("user", userAdminSession);

	when(loginService.getUserDetailsFromPrincipal(any(Principal.class))).thenReturn(userAdminDetails);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/").sessionAttrs(sessionAttr).principal(principal))
		.andExpect(redirectedUrl("/admin/home"));

	verify(loginService, times(1)).getUserDetailsFromPrincipal(any(Principal.class));
    }

    @Test
    public void loginControllerUserHome() throws Exception {

	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/user/home").sessionAttrs(sessionAttr))
		.andExpect(status().isOk()).andExpect(view().name("user/home"));
    }

    @Test
    public void loginControllerAdminHome() throws Exception {

	sessionAttr.put("user", userSession);

	mockMvc
		.perform(MockMvcRequestBuilders.get("/admin/home").sessionAttrs(sessionAttr))
		.andExpect(status().isOk()).andExpect(view().name("admin/home"));
    }
}