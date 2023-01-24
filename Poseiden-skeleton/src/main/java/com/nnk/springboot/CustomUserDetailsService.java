
package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

/**
 * Custom UserDetailsService class
 * 
 * @author trimok
 */
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * The user service
     */
    @Autowired
    private IUserService userService;

    /**
     * load the user by name and return UserDetails
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userService.findUserByName(username);
	SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(AUTHORITY_PREFIX + user.getRole());
	return new CustomUserDetails(user, grantedAuthority);
    }
}