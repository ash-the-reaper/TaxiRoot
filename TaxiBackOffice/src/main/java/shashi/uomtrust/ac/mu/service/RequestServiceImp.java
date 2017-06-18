package shashi.uomtrust.ac.mu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.dto.RequestDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.Request;
import shashi.uomtrust.ac.mu.repository.AccountRepository;
import shashi.uomtrust.ac.mu.repository.RequestRepository;

@Service
public class RequestServiceImp implements RequestService{
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public RequestDTO save(RequestDTO requestDTO) {
		Account account = accountRepository.findByAccountId(requestDTO.getAccountId());
		
		Request request = new Request();
		request.setAccount(account);
		request.setDate_created(new Date());
		
		if(requestDTO.getDateUpdated() != null)
			request.setDate_updated(requestDTO.getDateUpdated());
		else
			request.setDate_updated(new Date());
		
		request.setPlace_from(requestDTO.getPlaceFrom());
		request.setPlace_to(requestDTO.getPlaceTo());
		request.setRequest_status(requestDTO.getRequestStatus().getValue());
		
		Request newRequest = requestRepository.save(request);
		
		RequestDTO newRequestDTO = new RequestDTO();
		newRequestDTO.setAccountId(newRequest.getAccount().getAccountId());
		newRequestDTO.setDateCreated(newRequest.getDate_created());
		newRequestDTO.setDateUpdated(newRequest.getDate_updated());
		newRequestDTO.setPlaceFrom(newRequest.getPlace_from());
		newRequest.setPlace_to(newRequest.getPlace_to());
		newRequest.setRequest_status(newRequest.getRequest_status());
		newRequest.setRequest_id(newRequest.getRequest_id());
		
		return newRequestDTO;
	}

}