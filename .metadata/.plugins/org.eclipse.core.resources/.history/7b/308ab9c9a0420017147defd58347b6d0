package shashi.uomtrust.ac.mu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;


@Entity
public class Status {

	@Column(length = 100, nullable = false)
	private String statusDescription;
	
	@Id
	@Column(nullable = false)
	private UserStatus userStatus;

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

}
