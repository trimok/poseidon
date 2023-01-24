package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_OAUTH2_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_OIDC_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.IBidListService;
import com.nnk.springboot.services.ICurvePointService;
import com.nnk.springboot.services.ILoginService;
import com.nnk.springboot.services.IRatingService;
import com.nnk.springboot.services.IRuleNameService;
import com.nnk.springboot.services.ITradeService;
import com.nnk.springboot.services.IUserService;
import com.nnk.springboot.services.LoginService;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(value = org.junit.jupiter.api.MethodOrderer.MethodName.class)
public class ServiceTest {

    private Timestamp now = Timestamp.from(Instant.now());

    private IBidListService bidListService;
    private ICurvePointService curvePointService;
    private IRatingService ratingService;
    private IRuleNameService ruleNameService;
    private ITradeService tradeService;
    private IUserService userService;
    private ILoginService loginService;

    @Mock
    private BidListRepository bidListRepository;
    @Mock
    private CurvePointRepository curvePointRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private RuleNameRepository ruleNameRepository;
    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private UserRepository userRepository;

    private BidList bidListDatabase;
    private CurvePoint curvePointDatabase;
    private Rating ratingDatabase;
    private RuleName ruleNameDatabase;
    private Trade tradeDatabase;
    private User userDatabase;

    @BeforeEach
    public void beforeEach() {
	bidListService = new BidListService(bidListRepository);
	curvePointService = new CurvePointService(curvePointRepository);
	ratingService = new RatingService(ratingRepository);
	ruleNameService = new RuleNameService(ruleNameRepository);
	tradeService = new TradeService(tradeRepository);
	userService = new UserService(userRepository);
	loginService = new LoginService();

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

    /*********************** BIDLIST SERVICE **************************/

    @Test
    public void bidListServiceFindAllBidLists() {

	when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidListDatabase));
	List<BidList> bidLists = bidListService.findAllBidLists();
	verify(bidListRepository, times(1)).findAll();
	assertThat(bidLists).isEqualTo(Arrays.asList(bidListDatabase));
    }

    @Test
    public void bidListServiceAddBidList() {

	when(bidListRepository.save(any(BidList.class))).thenReturn(bidListDatabase);
	BidList bidList = bidListService.addBidList(bidListDatabase);
	verify(bidListRepository, times(1)).save(any(BidList.class));
	assertThat(bidList).isEqualTo(bidListDatabase);
    }

    @Test
    public void bidListServiceFindBidListById() {

	when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidListDatabase));
	BidList bidList = bidListService.findBidListById(1);
	verify(bidListRepository, times(1)).findById(any(Integer.class));
	assertThat(bidList).isEqualTo(bidListDatabase);
    }

    @Test
    public void bidListServiceUpdateBidList() {

	when(bidListRepository.save(any(BidList.class))).thenReturn(bidListDatabase);
	BidList bidList = bidListService.updateBidList(bidListDatabase);
	verify(bidListRepository, times(1)).save(any(BidList.class));
	assertThat(bidList).isEqualTo(bidListDatabase);
    }

    @Test
    public void bidListServiceDeleteBidList() {

	when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidListDatabase));
	bidListService.deleteBidList(1);
	verify(bidListRepository, times(1)).delete(any(BidList.class));
    }

    @Test
    public void bidListServiceDeleteAllBidList() {

	bidListService.deleteAllBidList();
	verify(bidListRepository, times(1)).deleteAll();
    }

    /*********************** CURVEPOINT SERVICE **************************/

    @Test
    public void curvePointServiceFindAllCurvePoints() {

	when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePointDatabase));
	List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
	verify(curvePointRepository, times(1)).findAll();
	assertThat(curvePoints).isEqualTo(Arrays.asList(curvePointDatabase));
    }

    @Test
    public void curvePointServiceAddCurvePoint() {

	when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePointDatabase);
	CurvePoint curvePoint = curvePointService.addCurvePoint(curvePointDatabase);
	verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
	assertThat(curvePoint).isEqualTo(curvePointDatabase);
    }

    @Test
    public void curvePointServiceFindCurvePointById() {

	when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointDatabase));
	CurvePoint curvePoint = curvePointService.findCurvePointById(1);
	verify(curvePointRepository, times(1)).findById(any(Integer.class));
	assertThat(curvePoint).isEqualTo(curvePointDatabase);
    }

    @Test
    public void curvePointServiceUpdateCurvePoint() {

	when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePointDatabase);
	CurvePoint curvePoint = curvePointService.updateCurvePoint(curvePointDatabase);
	verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
	assertThat(curvePoint).isEqualTo(curvePointDatabase);
    }

    @Test
    public void curvePointServiceDeleteCurvePoint() {

	when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointDatabase));
	curvePointService.deleteCurvePoint(1);
	verify(curvePointRepository, times(1)).delete(any(CurvePoint.class));
    }

    @Test
    public void curvePointServiceDeleteAllCurvePoint() {

	curvePointService.deleteAllCurvePoint();
	verify(curvePointRepository, times(1)).deleteAll();
    }

    /*********************** RATING SERVICE **************************/

    @Test
    public void ratingServiceFindAllRatings() {

	when(ratingRepository.findAll()).thenReturn(Arrays.asList(ratingDatabase));
	List<Rating> ratings = ratingService.findAllRatings();
	verify(ratingRepository, times(1)).findAll();
	assertThat(ratings).isEqualTo(Arrays.asList(ratingDatabase));
    }

    @Test
    public void ratingServiceAddRating() {

	when(ratingRepository.save(any(Rating.class))).thenReturn(ratingDatabase);
	Rating rating = ratingService.addRating(ratingDatabase);
	verify(ratingRepository, times(1)).save(any(Rating.class));
	assertThat(rating).isEqualTo(ratingDatabase);
    }

    @Test
    public void ratingServiceFindRatingById() {

	when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingDatabase));
	Rating rating = ratingService.findRatingById(1);
	verify(ratingRepository, times(1)).findById(any(Integer.class));
	assertThat(rating).isEqualTo(ratingDatabase);
    }

    @Test
    public void ratingServiceUpdateRating() {

	when(ratingRepository.save(any(Rating.class))).thenReturn(ratingDatabase);
	Rating rating = ratingService.updateRating(ratingDatabase);
	verify(ratingRepository, times(1)).save(any(Rating.class));
	assertThat(rating).isEqualTo(ratingDatabase);
    }

    @Test
    public void ratingServiceDeleteRating() {

	when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingDatabase));
	ratingService.deleteRating(1);
	verify(ratingRepository, times(1)).delete(any(Rating.class));
    }

    @Test
    public void ratingServiceDeleteAllRating() {

	ratingService.deleteAllRating();
	verify(ratingRepository, times(1)).deleteAll();
    }

    /*********************** RULENAME SERVICE **************************/

    @Test
    public void ruleNameServiceFindAllRuleNames() {

	when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleNameDatabase));
	List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
	verify(ruleNameRepository, times(1)).findAll();
	assertThat(ruleNames).isEqualTo(Arrays.asList(ruleNameDatabase));
    }

    @Test
    public void ruleNameServiceAddRuleName() {

	when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleNameDatabase);
	RuleName ruleName = ruleNameService.addRuleName(ruleNameDatabase);
	verify(ruleNameRepository, times(1)).save(any(RuleName.class));
	assertThat(ruleName).isEqualTo(ruleNameDatabase);
    }

    @Test
    public void ruleNameServiceFindRuleNameById() {

	when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameDatabase));
	RuleName ruleName = ruleNameService.findRuleNameById(1);
	verify(ruleNameRepository, times(1)).findById(any(Integer.class));
	assertThat(ruleName).isEqualTo(ruleNameDatabase);
    }

    @Test
    public void ruleNameServiceUpdateRuleName() {

	when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleNameDatabase);
	RuleName ruleName = ruleNameService.updateRuleName(ruleNameDatabase);
	verify(ruleNameRepository, times(1)).save(any(RuleName.class));
	assertThat(ruleName).isEqualTo(ruleNameDatabase);
    }

    @Test
    public void ruleNameServiceDeleteRuleName() {

	when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameDatabase));
	ruleNameService.deleteRuleName(1);
	verify(ruleNameRepository, times(1)).delete(any(RuleName.class));
    }

    @Test
    public void ruleNameServiceDeleteAllRuleName() {

	ruleNameService.deleteAllRuleName();
	verify(ruleNameRepository, times(1)).deleteAll();
    }

    /*********************** TRADE SERVICE **************************/

    @Test
    public void tradeServiceFindAllTrades() {

	when(tradeRepository.findAll()).thenReturn(Arrays.asList(tradeDatabase));
	List<Trade> trades = tradeService.findAllTrades();
	verify(tradeRepository, times(1)).findAll();
	assertThat(trades).isEqualTo(Arrays.asList(tradeDatabase));
    }

    @Test
    public void tradeServiceAddTrade() {

	when(tradeRepository.save(any(Trade.class))).thenReturn(tradeDatabase);
	Trade trade = tradeService.addTrade(tradeDatabase);
	verify(tradeRepository, times(1)).save(any(Trade.class));
	assertThat(trade).isEqualTo(tradeDatabase);
    }

    @Test
    public void tradeServiceFindTradeById() {

	when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeDatabase));
	Trade trade = tradeService.findTradeById(1);
	verify(tradeRepository, times(1)).findById(any(Integer.class));
	assertThat(trade).isEqualTo(tradeDatabase);
    }

    @Test
    public void tradeServiceUpdateTrade() {

	when(tradeRepository.save(any(Trade.class))).thenReturn(tradeDatabase);
	Trade trade = tradeService.updateTrade(tradeDatabase);
	verify(tradeRepository, times(1)).save(any(Trade.class));
	assertThat(trade).isEqualTo(tradeDatabase);
    }

    @Test
    public void tradeServiceDeleteTrade() {

	when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeDatabase));
	tradeService.deleteTrade(1);
	verify(tradeRepository, times(1)).delete(any(Trade.class));
    }

    @Test
    public void tradeServiceDeleteAllTrade() {

	tradeService.deleteAllTrade();
	verify(tradeRepository, times(1)).deleteAll();
    }

    /*********************** USER SERVICE **************************/

    @Test
    public void userServiceFindAllUsers() {

	when(userRepository.findAll()).thenReturn(Arrays.asList(userDatabase));
	List<User> users = userService.findAllUsers();
	verify(userRepository, times(1)).findAll();
	assertThat(users).isEqualTo(Arrays.asList(userDatabase));
    }

    @Test
    public void userServiceAddUser() {

	when(userRepository.save(any(User.class))).thenReturn(userDatabase);
	User user = userService.addUser(userDatabase);
	verify(userRepository, times(1)).save(any(User.class));
	assertThat(user).isEqualTo(userDatabase);
    }

    @Test
    public void userServiceFindUserById() {

	when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userDatabase));
	User user = userService.findUserById(1);
	verify(userRepository, times(1)).findById(any(Integer.class));
	assertThat(user).isEqualTo(userDatabase);
    }

    @Test
    public void userServiceUpdateUser() {

	when(userRepository.save(any(User.class))).thenReturn(userDatabase);
	User user = userService.updateUser(userDatabase);
	verify(userRepository, times(1)).save(any(User.class));
	assertThat(user).isEqualTo(userDatabase);
    }

    @Test
    public void userServiceDeleteUser() {

	when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userDatabase));
	userService.deleteUser(1);
	verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    public void userServiceDeleteAllUser() {

	userService.deleteAllUser();
	verify(userRepository, times(1)).deleteAll();
    }

    /*********************** LOGIN SERVICE **************************/

    @Test
    public void loginServiceGetUserDetails() {
	User user = new User(null, "guest", "password", "GUEST", AUTHORITY_USER);
	CustomUserDetails userDetails = new CustomUserDetails(user, AUTHORITY_USER);

	UsernamePasswordAuthenticationToken testingAuthenticationToken = new UsernamePasswordAuthenticationToken(
		userDetails,
		null, Arrays.asList(new SimpleGrantedAuthority(AUTHORITY_USER)));

	UserDetails usesrDetails = loginService.getUserDetailsFromPrincipal(testingAuthenticationToken);
	assertNotNull(usesrDetails);
    }

    @Test
    public void loginServiceGetStandardUserDetails() {
	User user = new User(null, "guest", "password", "GUEST", AUTHORITY_USER);
	CustomUserDetails userDetails = new CustomUserDetails(user, AUTHORITY_USER);

	UsernamePasswordAuthenticationToken testingAuthenticationToken = new UsernamePasswordAuthenticationToken(
		userDetails,
		null, Arrays.asList(new SimpleGrantedAuthority(AUTHORITY_USER)));

	UserDetails usesrDetails = loginService.getUserDetailsFromStandardPrincipal(testingAuthenticationToken);
	assertNotNull(usesrDetails);
    }

    @Test
    public void loginServiceOidc2UserDetails() {

	List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(AUTHORITY_OIDC_USER));
	Map<String, Object> claims = new HashMap<>();
	claims.put("name", "useroidc");
	claims.put("email", "useroidc@useroidc.mail");

	String userNameKey = "name";
	OidcIdToken oidcIdToken = new OidcIdToken("56465465456465", null, null, claims);
	DefaultOidcUser user = new DefaultOidcUser(authorities, oidcIdToken, userNameKey);
	OAuth2AuthenticationToken testingAuthenticationToken = new OAuth2AuthenticationToken(user, authorities,
		"sdcscd");

	UserDetails userDetails = loginService.getUserDetailsFromOauth2OidcPrincipal(testingAuthenticationToken);
	assertNotNull(userDetails);
    }

    @Test
    public void loginServiceOauth2UserDetails() {

	List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(AUTHORITY_OAUTH2_USER));

	OAuth2User user = new OAuth2User() {

	    Map<String, Object> attributes = new HashMap<>();

	    @Override
	    public Map<String, Object> getAttributes() {
		attributes.put("login", "userauth2_login");
		return attributes;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	    }

	    @Override
	    public String getName() {
		return "userauth2_name";
	    }
	};

	OAuth2AuthenticationToken testingAuthenticationToken = new OAuth2AuthenticationToken(user, authorities,
		"sdcscd");

	UserDetails userDetails = loginService.getUserDetailsFromOauth2OidcPrincipal(testingAuthenticationToken);
	assertNotNull(userDetails);
    }
}
