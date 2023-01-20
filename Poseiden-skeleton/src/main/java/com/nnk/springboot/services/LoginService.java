package com.nnk.springboot.services;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;

import lombok.extern.slf4j.Slf4j;

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
     * 
     * OAuth2 / OIDC login Getting a UserDetails object from a Principal
     * 
     * @param principal
     * @return
     */
    @Override
    public User getUserFromOauth2OidcPrincipal(Principal principal) {
	User user = new User();

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

	    }
	    log.info("OAuth2, but No OIDC login");
	}

	log.info("Connection, name :" + user.getUsername() + ", fullname : " + user.getFullname());

	return user;
    }
}
