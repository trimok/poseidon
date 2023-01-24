package com.nnk.springboot;

import static com.nnk.springboot.constants.Constants.AUTHORITY_ADMIN;
import static com.nnk.springboot.constants.Constants.AUTHORITY_OAUTH2_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_OIDC_USER;
import static com.nnk.springboot.constants.Constants.AUTHORITY_USER;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration class
 * 
 * @author trimok
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.nnk.springboot" })
public class CustomSecurityConfig {

    /**
     * Bean userDetailsService
     * 
     * @return an UserDetailsService interface
     */
    @Bean
    UserDetailsService CustomUserDetailsService() {
	CustomUserDetailsService userDetailsService = new CustomUserDetailsService();
	return userDetailsService;
    }

    /**
     * Configuring the filter chain
     * 
     * @param http : the http object
     * @return : SecurityFilterChain
     * @throws Exception : any exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	http.authorizeHttpRequests()
		.requestMatchers("/error").permitAll()
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		.requestMatchers("/admin/home", "/user/list", "/user/add",
			"/user/update/**, /user/delete/**,/user/validate")
		.hasAnyAuthority(AUTHORITY_ADMIN)
		.requestMatchers("/user/home", "/bidList/**", "/curvePoint/**", "/rating/**", "/trade/**",
			"/ruleName/**")
		.hasAnyAuthority(AUTHORITY_USER, AUTHORITY_OIDC_USER, AUTHORITY_OAUTH2_USER)
		.anyRequest().authenticated()
		.and().formLogin()
		.permitAll()
		.and().oauth2Login().userInfoEndpoint().userAuthoritiesMapper(this.userAuthoritiesMapper());

	http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true).permitAll()
		.and().csrf().disable();

	return http.build();
    }

    /**
     * userAuthoritiesMapper
     * 
     * @return mappedAuthorities
     */
    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
	return (authorities) -> {
	    Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

	    authorities.forEach(authority -> {
		if (OidcUserAuthority.class.isInstance(authority)) {
		    mappedAuthorities.add(new SimpleGrantedAuthority(AUTHORITY_OIDC_USER));
		} else if (OAuth2UserAuthority.class.isInstance(authority)) {
		    mappedAuthorities.add(new SimpleGrantedAuthority(AUTHORITY_OAUTH2_USER));
		}
	    });

	    return mappedAuthorities;
	};
    }

    /**
     * Password encoder
     * 
     * @return : a BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
}
