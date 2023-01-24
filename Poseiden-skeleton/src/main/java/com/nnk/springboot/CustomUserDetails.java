package com.nnk.springboot;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nnk.springboot.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Custom UserDetails
 * 
 * @author trimok
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    /**
     * serialVersionUID
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * userLogin
     */
    private User user = null;

    /**
     * authorities
     */
    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    /**
     * Constructor
     * 
     * @param user : user
     */
    public CustomUserDetails(User user) {
	this.user = user;
    }

    /**
     * constructor CustomUserDetails
     * 
     * @param user      : user
     * @param authority : authority
     */
    public CustomUserDetails(User user, GrantedAuthority authority) {
	this.user = user;
	addAuthority(authority);
    }

    /**
     * constructor CustomUserDetails
     * 
     * @param user            : the user
     * @param authorityString : string authority (ROLE_USER, etc..)
     */
    public CustomUserDetails(User user, String authorityString) {
	this.user = user;
	addAuthority(new SimpleGrantedAuthority(authorityString));
    }

    /**
     * getAuthorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
    }

    /**
     * Adding an authority
     * 
     * @param authority : authority
     */
    public void addAuthority(GrantedAuthority authority) {
	authorities.add(authority);
    }

    /**
     * hasAuthority
     * 
     * @param authorityName : authorityName
     * @return : boolean result
     */
    public boolean hasAuthority(String authorityName) {
	boolean ret = false;

	for (GrantedAuthority authority : authorities) {
	    if (authority.getAuthority().equals(authorityName)) {
		ret = true;
		break;
	    }
	}
	return ret;
    }

    /**
     * password
     */
    @Override
    public String getPassword() {
	return user.getPassword();
    }

    /**
     * username
     */
    @Override
    public String getUsername() {
	return user.getUsername();
    }

    /**
     * fullname
     * 
     * @return : fullname
     */
    public String getFullname() {
	return user.getFullname();
    }

    /**
     * isAccountNonExpired
     */
    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    /**
     * isAccountNonLocked
     */
    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    /**
     * isCredentialsNonExpired
     */
    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    /**
     * isEnabled
     */
    @Override
    public boolean isEnabled() {
	return true;
    }
}
