package shashi.uomtrust.ac.mu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value = "/getAllClients", method = RequestMethod.POST)
	public List<Account> getAllClients() {
		return clientService.getAllClient();
	}
	
	
	@RequestMapping(value = "/disableClient", method = RequestMethod.POST)
	public void disableClient(@RequestParam List<Long> id) {
		clientService.disableClient(id);
	}
	
}
