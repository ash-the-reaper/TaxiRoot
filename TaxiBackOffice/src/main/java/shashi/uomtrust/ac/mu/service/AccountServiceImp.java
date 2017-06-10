package shashi.uomtrust.ac.mu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.repository.AccountRepository;

@Service
public class AccountServiceImp implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account findByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return accountRepository.findByAccountId(accountId);
	}

	@Override
	public Account findByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account saveAccount(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.save(account);
	}

	@Override
	public boolean checkAdminLogin(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.checkAdminLogin(account.getEmail(), "admin")>0? true:false;
	}

}
