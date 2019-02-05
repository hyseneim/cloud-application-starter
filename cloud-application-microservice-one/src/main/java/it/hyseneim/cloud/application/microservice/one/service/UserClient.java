package it.hyseneim.cloud.application.microservice.one.service;

import it.hyseneim.cloud.application.common.dto.UserRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("oauth2-authorization-service")
public interface UserClient {

	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	UserRoleDTO findByUsername(
		@RequestParam("username") String username,
		@RequestHeader("Authorization") String token);

	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	UserRoleDTO findByUsername(
		@RequestParam("username") String username);

}