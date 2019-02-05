package it.hyseneim.cloud.application.oauth2.service;

import it.hyseneim.cloud.application.oauth2.model.OAuthUser;
import it.hyseneim.cloud.application.oauth2.model.OAuthUserRole;
import it.hyseneim.cloud.application.oauth2.repository.OAuthUserRepository;
import it.hyseneim.cloud.application.oauth2.repository.OAuthUserRoleRepository;
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
	private OAuthUserRepository _userRepository;

	@Autowired
	private OAuthUserRoleRepository _userRoleRepository;

	@Override
	public final UserDetails loadUserByUsername(
		String username)
		throws UsernameNotFoundException {
		final OAuthUser user =
			this._userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(
				"User '" + username + "' not found");
		}

		List<GrantedAuthority> authorities = new ArrayList<>(1);
		OAuthUserRole userRole =
			_userRoleRepository.findByUsername(username);
		if (userRole != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
		}

		return org.springframework.security.core.userdetails.User
			.withUsername(username).password(user.getPassword())
			.authorities(authorities).accountExpired(false)
			.accountLocked(false).credentialsExpired(false).disabled(
				!user.isEnabled())
			.build();
	}

}
