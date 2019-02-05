package it.hyseneim.cloud.application.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigJwt
	extends AuthorizationServerConfigurerAdapter {

	private static final String ALGORITHM = "HMACSHA512";

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Value("classpath:data.sql")
	private Resource dataScript;

	private String secret;

	@Autowired
	private CustomTokenEnhancer tokenEnhancer;

	@Autowired
	private DataSource dataSource;

	public OAuth2AuthorizationServerConfigJwt(AppConfig config) {
		this.secret = Base64.getEncoder()
			.encodeToString(config.getSecret().getBytes());
	}

	@Override
	public void configure(
		final AuthorizationServerSecurityConfigurer oauthServer)
		throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
			"isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients)
		throws Exception {
		clients.jdbc(dataSource);
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator =
			new ResourceDatabasePopulator();
		populator.addScript(dataScript);
		return populator;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(
		final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices =
			new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Override
	public void configure(
		final AuthorizationServerEndpointsConfigurer endpoints) {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(
			Arrays.asList(tokenEnhancer, accessTokenConverter()));
		endpoints.tokenStore(tokenStore()).tokenEnhancer(
			tokenEnhancerChain).authenticationManager(authenticationManager);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		byte[] secretDecoded = Base64.getDecoder().decode(this.secret);
		converter.setSigner(new MacSigner(
			ALGORITHM,
			new SecretKeySpec(secretDecoded, ALGORITHM)));
		return converter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
