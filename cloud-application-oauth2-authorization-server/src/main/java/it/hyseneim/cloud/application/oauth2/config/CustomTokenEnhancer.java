package it.hyseneim.cloud.application.oauth2.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(
		OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("organization", "Hyseneim Inc.");

		if (authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			additionalInfo.put("sub", user.getUsername());
		}

		DefaultOAuth2AccessToken result =
			((DefaultOAuth2AccessToken) accessToken);
		result.setAdditionalInformation(additionalInfo);

		return result;
	}
}
