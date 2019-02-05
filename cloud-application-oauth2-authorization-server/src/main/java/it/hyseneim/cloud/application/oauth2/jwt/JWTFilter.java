package it.hyseneim.cloud.application.oauth2.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

	public final static String AUTHORIZATION_HEADER = "Authorization";

	private final TokenProvider tokenProvider;

	public JWTFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response, FilterChain filterChain)
		throws IOException, ServletException {
		try {
			String jwt = resolveToken(request);
			if (jwt != null) {
				Authentication authentication = this.tokenProvider
					.getAuthentication(jwt);
				if (authentication != null) {
					SecurityContextHolder.getContext()
						.setAuthentication(authentication);
				}
			}
			filterChain.doFilter(request, response);
		}
		catch (ExpiredJwtException | UnsupportedJwtException
			| MalformedJwtException | SignatureException
			| UsernameNotFoundException e) {
			log.info("Security exception {}", e.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private static String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken)
			&& bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
