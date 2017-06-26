package shashi.uomtrust.ac.mu.controller;




import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
import shashi.uomtrust.ac.mu.utils.Utils;

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
	@RequestMapping(value = "/taxiCreateCarDetails", method = RequestMethod.POST)
	public CarDetailsDTO taxiCreateCarDetails(@RequestBody CarDetailsDTO carDetailsDTO){
		if(carDetailsDTO != null && carDetailsDTO.getAccountId() != null ){
			
			Utils.saveImageToServer(carDetailsDTO);
			return carDetailsService.saveCardetails(carDetailsDTO);
		}
		return null;
	}	 
}
