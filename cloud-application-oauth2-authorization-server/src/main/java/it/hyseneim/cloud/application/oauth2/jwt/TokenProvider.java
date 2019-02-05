package it.hyseneim.cloud.application.oauth2.jwt;

import it.hyseneim.cloud.application.oauth2.config.AppConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenProvider {

	private String secretKey;

	private final UserDetailsService userService;

	public TokenProvider(AppConfig config, UserDetailsService userService) {
		if (config.getSecret() != null) {
			this.secretKey = Base64.getEncoder()
				.encodeToString(config.getSecret().getBytes());
		}
		this.userService = userService;
	}

	public Authentication getAuthentication(String token) {
		String username = Jwts.parser().setSigningKey(this.secretKey)
			.parseClaimsJws(token).getBody().getSubject();
		UserDetails userDetails = this.userService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities());
	}

}
