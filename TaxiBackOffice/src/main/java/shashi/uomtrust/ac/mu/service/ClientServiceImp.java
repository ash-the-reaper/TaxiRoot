package shashi.uomtrust.ac.mu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;
import shashi.uomtrust.ac.mu.repository.AccountRepository;

@Service
public class ClientServiceImp implements ClientService{
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Account> getAllClient(UserRole userRole) {
		// TODO Auto-generated method stub
		return accountRepository.getAllUserDetails(userRole);
	}

	@Override
	@Transactional
	public void disableClient(List<Long> listId, UserRole userRole) {
		// TODO Auto-generated method stub
		accountRepository.disableClient(listId, userRole);
	}

	@Override
	@Transactional
	public void deleteClient(List<Long> listId, UserRole userRole) {
		// TODO Auto-generated method stub
		accountRepository.deleteClient(listId, userRole);
	}

	@Override
	@Transactional
	public void enableClient(List<Long> listId, UserRole userRole) {
		// TODO Auto-generated method stub
		accountRepository.enableClient(listId, userRole);
	}

	@Override
	public List<Account> getAllClientByStatus(UserStatus userStatus, UserRole userRole) {
		// TODO Auto-generated method stub
		return accountRepository.getAllUserDetailsByStatus(userStatus, userRole);
	}
}
