/**
 *
 */
package it.hyseneim.cloud.application.oauth2.repository;


import it.hyseneim.cloud.application.oauth2.model.OAuthUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthUserRoleRepository
	extends JpaRepository<OAuthUserRole, Long> {

	OAuthUserRole findByUsername(String username);

}
