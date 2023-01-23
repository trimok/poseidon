package com.nnk.springboot.services;

import java.security.Principal;

import com.nnk.springboot.CustomUserDetails;

public interface ILoginService {

    CustomUserDetails getUserDetailsFromPrincipal(Principal principal);

    CustomUserDetails getUserDetailsFromOauth2OidcPrincipal(Principal principal);

    CustomUserDetails getUserDetailsFromStandardPrincipal(Principal principal);
}