package shashi.uomtrust.ac.mu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.dto.RequestDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.Request;
import shashi.uomtrust.ac.mu.enums.RequestStatus;
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
		
		Calendar calendar = Calendar.getInstance();
		
		Request request = new Request();
		
		if(requestDTO.getRequestId() != null && requestDTO.getRequestId()>0)
			request.setRequest_id(requestDTO.getRequestId());
		
		request.setAccount(account);
		
		calendar.setTimeInMillis(requestDTO.getDateCreated());
		request.setDate_created(calendar.getTime());
		
		calendar.setTimeInMillis(requestDTO.getDateUpdated());
		request.setDate_updated(calendar.getTime());	
		
		calendar.setTimeInMillis(requestDTO.getEventDateTime());
		request.setEvent_date_time(calendar.getTime());
		
		request.setPlace_from(requestDTO.getPlaceFrom());
		request.setPlace_to(requestDTO.getPlaceTo());
		request.setRequest_status(requestDTO.getRequestStatus().getValue());
		request.setDetails(requestDTO.getDetails());
		
		Request newRequest = requestRepository.save(request);
		
		RequestDTO newRequestDTO = requestDTO;
		newRequestDTO.setRequestId(newRequest.getRequest_id());
		
		
		return newRequestDTO;
	}

	@Override
	public Boolean delete(Integer request_id) {
		// TODO Auto-generated method stub
		requestRepository.deleteRequestById(request_id);
		Request request = requestRepository.getRequestById(request_id);
		Boolean result = false;
		
		if(request == null || request.getRequest_id() == null)
			result = true;
		
		return result;
	}

	@Override
	public List<RequestDTO> getRequestByUserIdAndRequestStatus(RequestDTO requestDTO) {
		// TODO Auto-generated method stub
		List<Request> requestList = requestRepository.getRequestByUserIdAndRequestStatus(requestDTO.getAccountId(), requestDTO.getRequestStatus().getValue());
		
		List<RequestDTO> requestDTOs = new ArrayList<>();
		
		for(Request request : requestList){
			RequestDTO newRequestDTO = new RequestDTO();
			newRequestDTO.setAccountId(request.getAccount().getAccountId());
			newRequestDTO.setEventDateTime(request.getEvent_date_time().getTime());
			newRequestDTO.setPlaceFrom(request.getPlace_from());
			newRequestDTO.setPlaceTo(request.getPlace_to());
			newRequestDTO.setRequestId(request.getRequest_id());
			newRequestDTO.setDetails(request.getDetails());
			newRequestDTO.setRequestStatus(RequestStatus.valueFor(request.getRequest_status()));
			
			requestDTOs.add(newRequestDTO);
		}
		
		return requestDTOs;
	}
	
	@Override
	public List<RequestDTO> getRequestForTaxiByRequestStatus(RequestDTO requestDTO) {
		// TODO Auto-generated method stub
		List<Request> requestList = requestRepository.getRequestForTaxiByRequestStatus(requestDTO.getRequestStatus().getValue());
		
		List<RequestDTO> requestDTOs = new ArrayList<>();
		
		for(Request request : requestList){
			RequestDTO newRequestDTO = new RequestDTO();
			newRequestDTO.setAccountId(request.getAccount().getAccountId());
			newRequestDTO.setEventDateTime(request.getEvent_date_time().getTime());
			newRequestDTO.setPlaceFrom(request.getPlace_from());
			newRequestDTO.setPlaceTo(request.getPlace_to());
			newRequestDTO.setRequestId(request.getRequest_id());
			newRequestDTO.setDetails(request.getDetails());
			newRequestDTO.setRequestStatus(RequestStatus.valueFor(request.getRequest_status()));
			
			requestDTOs.add(newRequestDTO);
		}
		
		return requestDTOs;
	}

}
