package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_ADMIN;
import static com.nnk.springboot.constants.Constants.AUTHORITY_OAUTH2_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_OIDC_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(value = org.junit.jupiter.api.MethodOrderer.MethodName.class)
public class SecurityTest {

    private User userStandard;
    private User userAdmin;
    private User userOidc;
    private User userOauth2;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    private Map<String, Object> sessionAttrs = null;

    @BeforeEach
    public void beforeEach() {
	// STANDARD user
	userStandard = new User(null, "user", "@1Useruser", "USER", AUTHORITY_USER);
	userService.addUser(userStandard);

	// ADMIN user
	userAdmin = new User(null, "admin", "@1Adminadmin", "ADMIN", AUTHORITY_ADMIN);
	userService.addUser(userAdmin);

	// To get a unmodified password by BCrypt
	userStandard = new User(null, "user", "@1Useruser", "USER", AUTHORITY_USER);
	userAdmin = new User(null, "admin", "@1Adminadmin", "ADMIN", AUTHORITY_ADMIN);

	// Oidc, Oauth2
	userOidc = new User(null, "userOidc", "@1Oidcoidc", "USEROIDC", AUTHORITY_OIDC_USER);
	userOauth2 = new User(null, "userOauth2", "@1Oauth2oauth2", "USEROAUTH2", AUTHORITY_OAUTH2_USER);

	sessionAttrs = new HashMap<String, Object>();
    }

    @AfterEach
    public void afterEach() {
	userService.deleteAllUser();
    }

    @Test
    public void loginPage() throws Exception {
	mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void loginUserStandard() throws Exception {

	mockMvc.perform(formLogin().user(userStandard.getUsername()).password(
		userStandard.getPassword())).andExpect(authenticated());
    }

    @Test
    public void loginUserAdmin() throws Exception {

	mockMvc.perform(formLogin().user(userAdmin.getUsername()).password(
		userAdmin.getPassword())).andExpect(authenticated());
    }

    @Test
    public void loginUserOidc() throws Exception {

	List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(AUTHORITY_OIDC_USER));
	Map<String, Object> claims = new HashMap<>();
	claims.put("name", "useroidc");
	claims.put("email", "useroidc@useroidc.mail");

	String userNameKey = "name";
	OidcIdToken oidcIdToken = new OidcIdToken("56465465456465", null, null, claims);
	DefaultOidcUser user = new DefaultOidcUser(authorities, oidcIdToken, userNameKey);
	OAuth2AuthenticationToken testingAuthenticationToken = new OAuth2AuthenticationToken(user, authorities,
		"sdcscd");

	mockMvc.perform(get("/").with(authentication(testingAuthenticationToken)))
		.andExpect(authenticated());
    }

    public void loginUserOauth2() throws Exception {

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

	mockMvc.perform(get("/").with(authentication(testingAuthenticationToken)))
		.andExpect(authenticated());
    }

    @Test
    @WithMockUser(username = "standarduser", authorities = { AUTHORITY_USER })
    public void accessStandardUser() throws Exception {

	sessionAttrs.put("user", userStandard);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/list").sessionAttrs(sessionAttrs))
		.andExpect(view().name("bidList/list"))
		.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "oauth2user", authorities = { AUTHORITY_OAUTH2_USER })
    public void accessOAuth2User() throws Exception {

	sessionAttrs.put("user", userOauth2);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/list").sessionAttrs(sessionAttrs))
		.andExpect(view().name("bidList/list"))
		.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "oidcuser", authorities = { AUTHORITY_OIDC_USER })
    public void accessOIdc2User() throws Exception {
	sessionAttrs.put("user", userOidc);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/list").sessionAttrs(sessionAttrs))
		.andExpect(view().name("bidList/list"))
		.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "adminuser", authorities = { AUTHORITY_ADMIN })
    public void accessAdminUser() throws Exception {

	sessionAttrs.put("user", userAdmin);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/user/list").sessionAttrs(sessionAttrs))
		.andExpect(view().name("user/list"))
		.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "standarduser", authorities = { AUTHORITY_USER })
    public void errorStandardUser() throws Exception {

	sessionAttrs.put("user", userStandard);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/user/list").sessionAttrs(sessionAttrs))
		.andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "oauth2user", authorities = { AUTHORITY_OAUTH2_USER })
    public void errorOAuth2User() throws Exception {

	sessionAttrs.put("user", userOauth2);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/user/list").sessionAttrs(sessionAttrs))
		.andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "oidcuser", authorities = { AUTHORITY_OIDC_USER })
    public void errorOIdc2User() throws Exception {
	sessionAttrs.put("user", userOidc);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/user/list").sessionAttrs(sessionAttrs))
		.andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "adminuser", authorities = { AUTHORITY_ADMIN })
    public void errorAdminUser() throws Exception {

	sessionAttrs.put("user", userAdmin);

	mockMvc.perform(
		MockMvcRequestBuilders.post("/bidList/list").sessionAttrs(sessionAttrs))
		.andExpect(status().is(403));
    }
}
