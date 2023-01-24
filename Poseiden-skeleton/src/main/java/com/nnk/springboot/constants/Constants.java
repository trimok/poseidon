package com.nnk.springboot.constants;

/**
 * Constants
 * 
 * @author trimok
 *
 */
public class Constants {
    /**
     * Prefix for authority (USER is in the ROLE column of the table USER, but the
     * authority is ROLE_USER)
     */
    public final static String AUTHORITY_PREFIX = "ROLE_";
    /**
     * standard authority
     */
    public final static String AUTHORITY_USER = "ROLE_USER";
    /**
     * admin authority
     */
    public final static String AUTHORITY_ADMIN = "ROLE_ADMIN";
    /**
     * oidc user authority
     */
    public final static String AUTHORITY_OIDC_USER = "ROLE_OIDC";
    /**
     * oauth2 user authority
     */
    public final static String AUTHORITY_OAUTH2_USER = "ROLE_OAUTH2";
}
