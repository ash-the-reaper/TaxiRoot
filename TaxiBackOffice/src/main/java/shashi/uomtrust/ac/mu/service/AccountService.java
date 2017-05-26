package shashi.uomtrust.ac.mu.service;

import shashi.uomtrust.ac.mu.entity.Account;

public interface AccountService {

	//test commit
	public Account findById(Long id);
	public Account findByEmail(String email);
	public Account saveAccount(Account account);
	public boolean checkAdminLogin(Account account);
}
