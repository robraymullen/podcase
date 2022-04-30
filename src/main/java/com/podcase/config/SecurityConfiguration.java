package com.podcase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_ALLOW_LIST = { "/users/**", 
			"/podcasts/**", "/episodes/**", "/playstate/**",  "/download/monitor","/**"};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(AUTH_ALLOW_LIST).permitAll().antMatchers("/csrf")
				.permitAll().anyRequest().authenticated();
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
	}

}