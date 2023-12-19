package com.customerApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}


	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http)  throws Exception {
		http.authorizeHttpRequests()
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.permitAll()
		.and().logout().permitAll();
	return http.build();

	}


	 @Bean
	 public WebSecurityCustomizer ignoringCustomizer() {
	 	return (web) -> web.ignoring().requestMatchers("/img/**","/images/**", "/js/**","/style.css/**"
	 			,"/webjars/**","/styles.css/**","/fontawesome/**","/vendor/**","/webfonts/**");
	 }

}

//	@Bean
//	public SecurityFilterChain filterchain(HttpSecurity http)  throws Exception {
//		http.csrf().disable().authorizeHttpRequests()
//		.requestMatchers("/login").authenticated()
//		.anyRequest().permitAll()
//		.and()
//		.formLogin()
//				.loginPage("/login")
//				.usernameParameter("email")
//				.defaultSuccessUrl("/dashboard")
//				.permitAll()
//			.and()
//			.logout().permitAll()
//		;
//		return http.build();
//	}







