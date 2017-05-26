package shashi.uomtrust.ac.mu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shashi.uomtrust.ac.mu.dto.UserStatusAndUserTypeDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/getAllClients", method = RequestMethod.POST)
	public List<Account> getAllClients(@RequestBody UserRole userRole) {
		return clientService.getAllClient(userRole);
	}
	
    @CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/disableClient", method = RequestMethod.POST)
	public void disableClient(@RequestBody List<Long> listId, @RequestBody UserRole userRole) {
		clientService.disableClient(listId, userRole);
	}	
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/enableClient", method = RequestMethod.POST)
   	public void enableClient(@RequestBody List<Long> listId, @RequestBody UserRole userRole) {
   		clientService.enableClient(listId, userRole);
   	}	
    
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
   	public void deleteClient(@RequestBody List<Long> listId, @RequestBody UserRole userRole) {
    	//clientService.deleteClient(listId, userRole);
   	}	
    
    @CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/getAllClientByStatus", method = RequestMethod.POST)
	public List<Account> getAllClientByStatus(@RequestBody UserStatusAndUserTypeDTO userStatusAndUserTypeDTO) {
		return clientService.getAllClientByStatus(userStatusAndUserTypeDTO.getUserStatus(), userStatusAndUserTypeDTO.getUserRole());
	}
}
