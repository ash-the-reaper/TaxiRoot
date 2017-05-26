package shashi.uomtrust.ac.mu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.repository.AccountRepository;

@Service
public class ClientServiceImp implements ClientService{
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Account> getAllClient() {
		// TODO Auto-generated method stub
		return accountRepository.getAllUserDetails(UserRole.CLIENT);
	}

	@Override
	public void disableClient(List<Long> id) {
		// TODO Auto-generated method stub
		accountRepository.disableClient(id);
	}
}