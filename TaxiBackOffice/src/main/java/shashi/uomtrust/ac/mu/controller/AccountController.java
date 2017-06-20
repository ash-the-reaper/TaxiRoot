package shashi.uomtrust.ac.mu.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.Account;
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
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	public Boolean checkLogin(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null )
			return accountService.checkAdminLogin(account);
		return false;
	}

	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public Account createAccount(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null ){
			return accountService.saveAccount(account);
		}
		return null;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/checkAccountViaEmail", method = RequestMethod.POST)
	public Account checkAccountViaEmail(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null ){
			return accountService.findByEmail(account.getEmail());
		}
		return null;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createCarDetails", method = RequestMethod.POST)
	public CarDetailsDTO createCarDetails(@RequestBody CarDetailsDTO carDetailsDTO){
		if(carDetailsDTO != null && carDetailsDTO.getAccountId() != null ){	
			
			if(carDetailsDTO.getPicture1() != null){
				carDetailsDTO.setPicture1(Base64.decodeBase64(carDetailsDTO.getPicture1()));
			}
			
			if(carDetailsDTO.getPicture2() != null){
				carDetailsDTO.setPicture2(Base64.decodeBase64(carDetailsDTO.getPicture2()));
			}
			
			if(carDetailsDTO.getPicture3() != null){
				carDetailsDTO.setPicture3(Base64.decodeBase64(carDetailsDTO.getPicture3()));
			}
			
			if(carDetailsDTO.getPicture4() != null){
				carDetailsDTO.setPicture4(Base64.decodeBase64(carDetailsDTO.getPicture4()));
			}
			
			
			return carDetailsService.saveCardetails(carDetailsDTO);
		}
		return null;
	}
}
