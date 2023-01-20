package com.nnk.springboot.services;

import java.security.Principal;

import com.nnk.springboot.domain.User;

public interface ILoginService {
    /**
     * 
     * OAuth2 / OIDC login Getting a UserDetails object from a Principal
     * 
     * @param principal
     * @return
     */
    User getUserFromOauth2OidcPrincipal(Principal principal);
}