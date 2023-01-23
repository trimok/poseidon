package com.nnk.springboot;

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
    private CustomUserDetails userDetails;
    private HashMap<String, Object> sessionAttr;

    private BidList bidListDatabase;

    @BeforeEach
    public void beforeEach() {
	userSession = new User(null, "guest", "password", "GUEST", AUTHORITY_USER);
	userDetails = new CustomUserDetails(userSession, AUTHORITY_USER);

	sessionAttr = new HashMap<String, Object>();
	sessionAttr.put("user", userSession);

	bidListDatabase = new BidList(null, "account", "type", 1d, 1d, 1d, 1d, "benchmark", now,
		"commentary", "security",
		"status", "trader", "book", "creationName", now, "revisionName",
		now, "dealName", "dealType",
		"sourceListId", "side");
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

    @Test
    public void testBidListControllerValidate() throws Exception {

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/validate").sessionAttrs(sessionAttr).flashAttr("bidList",
			bidListDatabase))
		.andExpect(redirectedUrl("/bidList/list"));

	List<BidList> bidLists = bidListService.findAllBidLists();
	assertThat(bidLists.size()).isEqualTo(1);

	BidList bidList = bidLists.get(0);
	assertThat(bidList).isEqualTo(bidListDatabase);
    }
}
