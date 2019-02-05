package it.hyseneim.cloud.application.microservice.one.service;

import it.hyseneim.cloud.application.common.dto.UserRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserClient _userClient;

	public final UserDetails loadUserByUsernameWithToken(
		String username, String token)
		throws UsernameNotFoundException {
		final UserRoleDTO userRole;
		if (token != null) {
			String realToken = "Bearer " + token;

			userRole = this._userClient.findByUsername(username, realToken);
		}
		else {
			userRole = this._userClient.findByUsername(username);
		}

		if (userRole == null) {
			throw new UsernameNotFoundException(
				"User '" + username + "' not found");
		}

		List<GrantedAuthority> authorities = new ArrayList<>(1);
		if (userRole.getRole() != null) {
			authorities.add(new SimpleGrantedAuthority(
				"ROLE_" + userRole.getRole().getRole()));
		}

		return org.springframework.security.core.userdetails.User
			.withUsername(username).password(userRole.getUser().getPassword())
			.authorities(authorities).accountExpired(false)
			.accountLocked(false).credentialsExpired(false).disabled(
				!userRole.getUser().isEnabled())
			.build();
	}

	@Override
	public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {
		return loadUserByUsernameWithToken(username, null);
	}

}
