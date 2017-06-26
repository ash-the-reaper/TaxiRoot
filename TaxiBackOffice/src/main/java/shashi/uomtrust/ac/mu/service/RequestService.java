package shashi.uomtrust.ac.mu.service;

import java.util.List;

import shashi.uomtrust.ac.mu.dto.RequestDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.Request;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;

public interface RequestService {

	public RequestDTO save(RequestDTO requestDTO);
	public Boolean delete(Integer requestId);
	public List<RequestDTO> getRequestByUserIdAndRequestStatus(RequestDTO requestDTO);
	public List<RequestDTO> getOtherRequestByUserIdAndRequestStatus(RequestDTO requestDTO);
	public List<RequestDTO> getPendingRequestListTaxi(RequestDTO requestDTO);
	public RequestDTO acceptOrRejectRequestTaxi(RequestDTO requestDTO);
	public RequestDTO acceptOrRejectRequestUser(RequestDTO requestDTO);

}
