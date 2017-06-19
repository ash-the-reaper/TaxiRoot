package shashi.uomtrust.ac.mu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shashi.uomtrust.ac.mu.dto.ManageRequestDTO;
import shashi.uomtrust.ac.mu.dto.RequestDTO;
import shashi.uomtrust.ac.mu.service.ClientService;
import shashi.uomtrust.ac.mu.service.ManageRequestService;
import shashi.uomtrust.ac.mu.service.RequestService;

@RestController
@RequestMapping("/api/request")
public class RequestController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ManageRequestService manageRequestService;
    
    @CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createUpdateRequest", method = RequestMethod.POST)
	public RequestDTO createUpdateRequest(@RequestBody RequestDTO requestDTO) {    	
		return requestService.save(requestDTO);
	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/deleteRequest", method = RequestMethod.POST)
   	public Boolean deleteRequest(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.delete(requestDTO.getRequestId());
   	}
    
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/getRequestListUser", method = RequestMethod.POST)
   	public List<RequestDTO> getRequestListUser(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.getRequestByUserIdAndRequestStatus(requestDTO);
   	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/getRequestListTaxi", method = RequestMethod.POST)
   	public List<RequestDTO> getRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.getRequestForTaxiByRequestStatus(requestDTO);
   	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/acceptRequestTaxi", method = RequestMethod.POST)
   	public ManageRequestDTO acceptRequestTaxi(@RequestBody ManageRequestDTO manageRequestDTO) {    	    	
    	return manageRequestService.save(manageRequestDTO);
   	}
}