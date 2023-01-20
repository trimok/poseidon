package com.nnk.springboot;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nnk.springboot.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    /*
     * 
     */
    public CustomUserDetails(User user) {
	this.user = user;
    }

    /**
     * 
     * @param user
     * @param authority
     */
    public CustomUserDetails(User user, GrantedAuthority authority) {
	this.user = user;
	addAuthority(authority);
    }

    /*
     * 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
    }

    /*
     * Adding an authority
     */
    public void addAuthority(GrantedAuthority authority) {
	authorities.add(authority);
    }

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
     * email
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
