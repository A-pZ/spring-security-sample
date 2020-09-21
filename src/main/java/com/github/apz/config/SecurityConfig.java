package com.github.apz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * SpringSecurity設定。
 * https://github.com/spring-projects/spring-security/blob/5.4.0/samples/boot/helloworld/src/main/java/org/springframework/security/samples/config/SecurityConfig.java
 * @author a-pz
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests(
					(authorize) -> authorize
					.antMatchers("/webjars/**","/index","/static/**").permitAll()
					.antMatchers("/upload/**").hasRole("USER")
				).formLogin(
					(formLogin) -> formLogin
					.loginPage("/login")
					.failureUrl("/login-error")
				);
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}
}
