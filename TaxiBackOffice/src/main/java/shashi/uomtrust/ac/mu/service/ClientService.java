package shashi.uomtrust.ac.mu.service;

import java.util.List;

import shashi.uomtrust.ac.mu.entity.Account;

public interface ClientService {

	public List<Account> getAllClient();
	public void disableClient(List<Long> id);
}