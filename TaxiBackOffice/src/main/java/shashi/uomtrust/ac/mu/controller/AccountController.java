package shashi.uomtrust.ac.mu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.service.AccountService;
import shashi.uomtrust.ac.mu.service.CarDetailsService;
import shashi.uomtrust.ac.mu.service.ClientService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ClientService ClientService;
	
	@Autowired
	private CarDetailsService carDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//admin root
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	public Account createAdmin(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null )
			return accountService.saveAccount(account);
		return null;
		
	}
	
	//@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	public Boolean checkLogin(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null )
			return accountService.checkAdminLogin(account);
		return false;
	}

	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public Account createAccount(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null ){
			return accountService.saveAccount(account);
		}
		return null;
	}
	
	@RequestMapping(value = "/createCarDetails", method = RequestMethod.POST)
	public Integer createCarDetails(@RequestBody CarDetailsDTO carDetailsDTO){
		if(carDetailsDTO != null && carDetailsDTO.getAccountId() != null ){			
			return carDetailsService.saveCardetails(carDetailsDTO);
		}
		return null;
	}
}
