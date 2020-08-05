package com.jrp.pma.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;

	@Autowired
	AuthenticationSuccessHandler successHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.usersByUsernameQuery("select username, password, enabled "+
				"from user_accounts where username = ?" )
			.authoritiesByUsernameQuery("select username, role "+
				"from user_accounts where username = ?")
			.dataSource(dataSource)
			.passwordEncoder(bCryptEncoder);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
				.antMatchers("/employees/**").hasRole("ADMIN")
				.antMatchers("/projects/**").hasAnyRole("ADMIN","USER")
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/").hasRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/LoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.successHandler(successHandler)
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/errorpages/error-401");

		http.csrf().disable();
		http.headers().frameOptions().disable();


	}
	
}
