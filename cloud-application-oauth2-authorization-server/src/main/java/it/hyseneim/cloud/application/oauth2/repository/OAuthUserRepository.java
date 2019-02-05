/**
 *
 */
package it.hyseneim.cloud.application.oauth2.repository;


import it.hyseneim.cloud.application.oauth2.model.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {

	OAuthUser findByUsername(String username);

}
