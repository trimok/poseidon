
package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.ROLE_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userService.findUserByName(username);
	SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole());
	return new CustomUserDetails(user, grantedAuthority);
    }
}