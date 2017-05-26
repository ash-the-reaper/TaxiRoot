package shashi.uomtrust.ac.mu.service;

import java.util.List;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;

public interface ClientService {

	public List<Account> getAllClient(UserRole useRole);
	public List<Account> getAllClientByStatus(UserStatus userStatus, UserRole userRole);
	public void disableClient(List<Long> listId, UserRole userRole);
	public void deleteClient(List<Long> listId, UserRole userRole);
	public void enableClient(List<Long> listId, UserRole userRole);

}
