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
	@RequestMapping(value = "/userCreateUpdateRequest", method = RequestMethod.POST)
	public RequestDTO userCreateUpdateRequest(@RequestBody RequestDTO requestDTO) {    	
		return requestService.save(requestDTO);
	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/userDeleteRequest", method = RequestMethod.POST)
   	public Boolean userDeleteRequest(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.delete(requestDTO.getRequestId());
   	}
    
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/userGetPendingRequestList", method = RequestMethod.POST)
   	public List<RequestDTO> userGetPendingRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.getRequestByUserIdAndRequestStatus(requestDTO);
   	}
    
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/taxiGetPendingRequestList", method = RequestMethod.POST)
   	public List<RequestDTO> taxiGetPendingRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.getPendingRequestListTaxi(requestDTO);
   	}
   
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/taxiGetOtherRequestList", method = RequestMethod.POST)
   	public List<RequestDTO> taxiGetOtherRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return manageRequestService.getManageRequestByStatusForTaxi(requestDTO.getRequestStatus().getValue(), requestDTO.getAccountId());
   	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/userGetOtherRequestList", method = RequestMethod.POST)
   	public List<RequestDTO> userGetOtherRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return manageRequestService.getManageRequestByStatusForUser(requestDTO.getRequestStatus().getValue(), requestDTO.getAccountId());
   	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/acceptOrRejectRequestTaxi", method = RequestMethod.POST)
   	public RequestDTO acceptOrRejectRequestTaxi(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.acceptOrRejectRequestTaxi(requestDTO);
   	}
    
    @CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/acceptOrRejectRequestUser", method = RequestMethod.POST)
   	public RequestDTO acceptOrRejectRequestUser(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.acceptOrRejectRequestUser(requestDTO);
   	}
}
