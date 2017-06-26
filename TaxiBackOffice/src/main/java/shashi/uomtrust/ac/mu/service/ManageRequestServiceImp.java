package shashi.uomtrust.ac.mu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.dto.ManageRequestDTO;
import shashi.uomtrust.ac.mu.dto.RequestDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.entity.ManageRequest;
import shashi.uomtrust.ac.mu.entity.Request;
import shashi.uomtrust.ac.mu.enums.RequestStatus;
import shashi.uomtrust.ac.mu.repository.AccountRepository;
import shashi.uomtrust.ac.mu.repository.CarDetailsRepository;
import shashi.uomtrust.ac.mu.repository.ManageRequestRepository;
import shashi.uomtrust.ac.mu.repository.RequestRepository;

@Service
public class ManageRequestServiceImp implements ManageRequestService{
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CarDetailsRepository carDetailsRepository;
	
	@Autowired
	private ManageRequestRepository manageRequestRepository;
	

	@Override
	public ManageRequestDTO save(ManageRequestDTO manageRequestDTO) {
		// TODO Auto-generated method stub
		
		ManageRequest manageRequest = new ManageRequest();
		
		CarDetails carDetails = carDetailsRepository.getCarById(manageRequestDTO.getCarId());
		manageRequest.setCarDetails(carDetails);
		
		Account account = accountRepository.findByAccountId(manageRequestDTO.getAccountId());
		manageRequest.setUserAccount(account);
		
		Request request = requestRepository.getRequestById(manageRequestDTO.getRequestId());
		manageRequest.setRequest(request);
		
		Calendar calendar = Calendar.getInstance();
		
		if(manageRequestDTO.getDateCreated() != null){
			calendar.setTimeInMillis(manageRequestDTO.getDateCreated().getTime());
			manageRequest.setDate_created(new Date());
		}
		
		if(manageRequestDTO.getDateUpdated() != null){
			calendar.setTimeInMillis(manageRequestDTO.getDateUpdated().getTime());
			manageRequest.setDate_updated(new Date());
		}
		
		manageRequest.setPrice(manageRequestDTO.getPrice());
		manageRequest.setRequest_status(manageRequestDTO.getRequestStatus().getValue());
		
		ManageRequest newManageRequest = manageRequestRepository.save(manageRequest);
		
		ManageRequestDTO newManageRequestDTO = new ManageRequestDTO();
		newManageRequestDTO.setAccountId(newManageRequest.getUserAccount().getAccountId());
		newManageRequestDTO.setCarId(newManageRequest.getCarDetails().getCarId());
		newManageRequestDTO.setManageRequestId(newManageRequest.getManage_request_id());
		newManageRequestDTO.setPrice(newManageRequest.getPrice());
		newManageRequestDTO.setRequestStatus(RequestStatus.valueFor(newManageRequest.getRequest_status()));
		newManageRequestDTO.setRequestId(newManageRequest.getRequest().getRequest_id());
		
		return newManageRequestDTO;
	}


	@Override
	public List<RequestDTO> getManageRequestByStatusForTaxi(Integer request_status, Integer account_id) {
		// TODO Auto-generated method stub
		
		Account account = accountRepository.findByAccountId(account_id);
		CarDetails carDetails = carDetailsRepository.getCarByAccountId(account_id);
		List<ManageRequest> manageRequestList = manageRequestRepository.getManageRequestByStatusForTaxi(request_status, carDetails);
		
		List<RequestDTO> finalRequestList = new ArrayList();
		
		if(manageRequestList != null && manageRequestList.size() >0){
			for(ManageRequest manageRequest : manageRequestList){
				Request request = manageRequest.getRequest();
				
				RequestDTO newRequestDTO = new RequestDTO();
				newRequestDTO.setAccountId(request.getAccount().getAccountId());
				newRequestDTO.setEventDateTime(request.getEvent_date_time().getTime());
				newRequestDTO.setPlaceFrom(request.getPlace_from());
				newRequestDTO.setPlaceTo(request.getPlace_to());
				newRequestDTO.setRequestId(request.getRequest_id());
				newRequestDTO.setDetails(request.getDetails());
				newRequestDTO.setRequestStatus(RequestStatus.valueFor(manageRequest.getRequest_status()));
				newRequestDTO.setCarId(manageRequest.getCarDetails().getCarId());
				
				newRequestDTO.setPrice(manageRequest.getPrice());

				
				finalRequestList.add(newRequestDTO);
			}
		}		
		return finalRequestList;
	}
	
	@Override
	public List<RequestDTO> getManageRequestByStatusForUser(Integer request_status, Integer account_id) {
		// TODO Auto-generated method stub
		Account account = accountRepository.findByAccountId(account_id);
		List<ManageRequest> manageRequestList = manageRequestRepository.getManageRequestByStatusForUser(request_status, account);
		
		List<RequestDTO> finalRequestList = new ArrayList();
		
		if(manageRequestList != null && manageRequestList.size() >0){
			for(ManageRequest manageRequest : manageRequestList){
				Request request = manageRequest.getRequest();
				
				RequestDTO newRequestDTO = new RequestDTO();
				newRequestDTO.setAccountId(request.getAccount().getAccountId());
				newRequestDTO.setEventDateTime(request.getEvent_date_time().getTime());
				newRequestDTO.setPlaceFrom(request.getPlace_from());
				newRequestDTO.setPlaceTo(request.getPlace_to());
				newRequestDTO.setRequestId(request.getRequest_id());
				newRequestDTO.setDetails(request.getDetails());
				newRequestDTO.setRequestStatus(RequestStatus.valueFor(manageRequest.getRequest_status()));
				newRequestDTO.setPrice(manageRequest.getPrice());
				newRequestDTO.setCarId(manageRequest.getCarDetails().getCarId());

				String firstName = manageRequest.getCarDetails().getAccount().getFirstName();
				String lastName = manageRequest.getCarDetails().getAccount().getLastName();
				
				newRequestDTO.setDriverName(firstName + " "+lastName);

				finalRequestList.add(newRequestDTO);
			}
		}		
		return finalRequestList;
	}
	
	

}
