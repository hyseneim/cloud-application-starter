package it.hyseneim.cloud.application.oauth2.controller;

import it.hyseneim.cloud.application.common.dto.RoleDTO;
import it.hyseneim.cloud.application.common.dto.UserDTO;
import it.hyseneim.cloud.application.common.dto.UserRoleDTO;
import it.hyseneim.cloud.application.oauth2.model.OAuthUser;
import it.hyseneim.cloud.application.oauth2.model.OAuthUserRole;
import it.hyseneim.cloud.application.oauth2.repository.OAuthUserRepository;
import it.hyseneim.cloud.application.oauth2.repository.OAuthUserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private ModelMapper _modelMapper;

	@Autowired
	private OAuthUserRepository _userRepository;

	@Autowired
	private OAuthUserRoleRepository _roleRepository;

	@GetMapping("/users/{username}")
	public UserRoleDTO findByUsername(String username) {
		OAuthUser user = _userRepository.findByUsername(username);
		if (user != null) {
			OAuthUserRole userRole =
				_roleRepository.findByUsername(username);

			UserDTO userDTO = new UserDTO();
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			_modelMapper.map(user, userDTO);
			userRoleDTO.setUser(userDTO);

			if (userRole != null) {
				RoleDTO roleDTO = new RoleDTO();
				_modelMapper.map(userRole, roleDTO);
				userRoleDTO.setRole(roleDTO);
			}

			return userRoleDTO;
		}

		return null;

	}

}
