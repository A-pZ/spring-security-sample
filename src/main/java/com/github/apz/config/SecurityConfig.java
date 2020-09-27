package com.github.apz.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * SpringSecurity設定。
 * https://github.com/spring-projects/spring-security/blob/5.4.0/samples/boot/helloworld/src/main/java/org/springframework/security/samples/config/SecurityConfig.java
 * @author a-pz
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests(
					(authorize) -> authorize
					.antMatchers("/webjars/**","/", "/index","/static/**","/error").permitAll()
					.anyRequest().authenticated()
			)
			.logout(logout -> logout.logoutSuccessUrl("/logout").permitAll())

			.exceptionHandling(h -> h.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
			.oauth2Login().defaultSuccessUrl("/index");
	}
}
