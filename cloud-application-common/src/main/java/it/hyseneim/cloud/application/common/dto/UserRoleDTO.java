package it.hyseneim.cloud.application.common.dto;

public class UserRoleDTO {

	private UserDTO user;

	private RoleDTO role;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

}
