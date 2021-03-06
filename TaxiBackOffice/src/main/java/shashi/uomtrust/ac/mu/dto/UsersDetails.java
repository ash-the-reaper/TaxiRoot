package shashi.uomtrust.ac.mu.dto;

import java.util.List;

import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;

public class UsersDetails {
	
	private UserStatus userStatus;
	private UserRole userRole;
	private List<Long> listId;
	
	public List<Long> getListId() {
		return listId;
	}
	public void setListId(List<Long> listId) {
		this.listId = listId;
	}
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}
