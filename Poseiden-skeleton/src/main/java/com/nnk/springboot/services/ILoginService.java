package com.nnk.springboot.services;

import java.security.Principal;

import com.nnk.springboot.CustomUserDetails;

/**
 * The ILoginService interface
 * 
 * @author trimok
 */
public interface ILoginService {

    /**
     * getUserDetailsFromPrincipal
     * 
     * @param principal : the principal
     * @return : a UserDetails interface
     */
    CustomUserDetails getUserDetailsFromPrincipal(Principal principal);

    /**
     * getUserDetailsFromOauth2OidcPrincipal
     * 
     * @param principal : the principal
     * @return : a UserDetails interface
     */
    CustomUserDetails getUserDetailsFromOauth2OidcPrincipal(Principal principal);

    /**
     * getUserDetailsFromStandardPrincipal
     * 
     * @param principal : the principal
     * @return : a UserDetails interface
     */
    CustomUserDetails getUserDetailsFromStandardPrincipal(Principal principal);
}