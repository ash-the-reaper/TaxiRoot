package shashi.uomtrust.ac.mu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import shashi.uomtrust.ac.mu.enums.UserRole;


@Entity
public class Role {

	@Column(length = 100, nullable = false)
	private String roleDescription;
	
	@Id
	@Column(nullable = false)
	private UserRole userRole;
	

	public String getRoleDescription() {
		return roleDescription;
	}


	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}


	public UserRole getRole() {
		return userRole;
	}


	public void setRole(UserRole userRole) {
		this.userRole = userRole;
	}	

	public Role() {
		
	}

}