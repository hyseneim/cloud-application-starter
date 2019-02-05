package it.hyseneim.cloud.application.oauth2.config;

import it.hyseneim.cloud.application.oauth2.jwt.JWTConfigurer;
import it.hyseneim.cloud.application.oauth2.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final TokenProvider tokenProvider;

	public WebSecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth)
		throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password(passwordEncoder.encode("1234")).roles(
			"ADMIN").and()
			.withUser("test").password(passwordEncoder.encode("1234")).roles(
			"USER");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/login").permitAll()
			.antMatchers(
				"/oauth/token/revokeById/**",
				"/swagger/v2/api-docs").permitAll()
			.antMatchers("/tokens/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.apply(new JWTConfigurer(this.tokenProvider));
	}

}
