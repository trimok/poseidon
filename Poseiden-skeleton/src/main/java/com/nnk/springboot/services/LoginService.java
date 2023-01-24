package com.nnk.springboot.services;

import static com.nnk.springboot.constants.Constants.AUTHORITY_OAUTH2_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_OIDC_USER;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.nnk.springboot.CustomUserDetails;
import com.nnk.springboot.domain.User;

import lombok.extern.slf4j.Slf4j;

/**
 * The Login Service
 * 
 * @author trimok
 */
@Service
@Slf4j
public class LoginService implements ILoginService {

    /**
     * 
     * Get a identification token from an authentication token
     * 
     * @param authToken : the authorization token
     * @return : the identifiation token
     */
    private OidcIdToken getIdToken(OAuth2AuthenticationToken authToken) {
	OAuth2User principal = authToken.getPrincipal();
	if (principal instanceof DefaultOidcUser) {
	    DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
	    return oidcUser.getIdToken();
	}
	return null;
    }

    /**
     * getUserDetailsFromPrincipal
     */
    @Override
    public CustomUserDetails getUserDetailsFromPrincipal(Principal principal) {

	CustomUserDetails userDetails = null;

	// Get the Person Object from different type of login
	if (principal instanceof UsernamePasswordAuthenticationToken) {
	    // Basic login
	    userDetails = getUserDetailsFromStandardPrincipal(principal);
	} else if (principal instanceof OAuth2AuthenticationToken) {
	    // OAuth / OIDC login
	    userDetails = getUserDetailsFromOauth2OidcPrincipal(principal);
	}
	return userDetails;
    }

    /**
     * 
     * OAuth2 / OIDC login Getting a UserDetails object from a Principal
     * 
     * @param principal : the principal
     * @return : CustomUserDetails
     */
    @Override
    public CustomUserDetails getUserDetailsFromOauth2OidcPrincipal(Principal principal) {
	User user = new User();
	CustomUserDetails userDetails = new CustomUserDetails();

	// Authentication token
	OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) principal);

	// Principal
	OAuth2User oauth2User = authToken.getPrincipal();

	// OAuth2 infos
	Map<String, Object> userAttributes = null;
	if (authToken.isAuthenticated()) {
	    // Data principal
	    userAttributes = oauth2User.getAttributes();

	} else {
	    log.error("Not Authenticated");
	    return null;
	}

	// OIDC Infos
	Map<String, Object> claims = null;
	OidcIdToken idToken = getIdToken(authToken);
	if (idToken != null) {
	    // Claims
	    claims = idToken.getClaims();

	    // Get the name / email infos from the claims
	    user.setUsername((String) claims.get("name"));
	    user.setFullname((String) claims.get("email"));

	    // Create CustomUserDetails
	    userDetails = new CustomUserDetails(user, AUTHORITY_OIDC_USER);

	    log.info("OAuth2 / OIDC login");

	} else {
	    // Only OAuth2
	    // Get the name info from the login name or login
	    String name;
	    if (userAttributes != null) {
		name = (String) userAttributes.get("name");
		if (name == null) {
		    name = (String) userAttributes.get("login");
		}
		// Email is very likely to be null, here
		String email = (String) userAttributes.get("email");

		user.setUsername(name);
		user.setFullname(email);

		// Create CustomUserDetails
		userDetails = new CustomUserDetails(user, AUTHORITY_OAUTH2_USER);
	    }
	    log.info("OAuth2, but No OIDC login");
	}

	log.info("Connection, name :" + userDetails.getUsername() + ", fullname : " + userDetails.getFullname());

	return userDetails;
    }

    /**
     * 
     * standard user Getting a UserDetails object from a Principal
     * 
     * @param principal : the principal
     * @return : CustomUserDetails
     */
    @Override
    public CustomUserDetails getUserDetailsFromStandardPrincipal(Principal principal) {
	CustomUserDetails userDetails = null;

	// Get the authentication token
	UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) principal);
	if (token.isAuthenticated()) {
	    // Get the principal
	    userDetails = (CustomUserDetails) token.getPrincipal();

	    log.info("Basic login");
	    log.info("Connection, name :" + userDetails.getUsername() + ", fullname : " + userDetails.getFullname());
	} else {
	    log.error("Not Authenticated");
	    return null;
	}
	return userDetails;
    }
}
