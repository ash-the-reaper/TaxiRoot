package shashi.uomtrust.ac.mu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.entity.Request;
import shashi.uomtrust.ac.mu.enums.RequestStatus;
import shashi.uomtrust.ac.mu.enums.UserRole;



@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {	
	
	@Modifying
	@Query("update Request r set r.request_status = :request_status where r.request_id =:request_id")
	@Transactional
	public void updateRequestStatustById(@Param("request_id") Integer request_id, @Param("request_status") RequestStatus request_status );
	
	
	@Modifying
	@Query("delete from Request r where r.request_id =:request_id")
	@Transactional
	public void deleteRequestById(@Param("request_id") Integer request_id);
	
	
	@Query("select r from Request r where r.request_id =:request_id")
	public Request getRequestById(@Param("request_id") Integer request_id);
	
	
	@Query("select r from Request r where r.request_status =:request_status")
	public List<Request> getListRequestsByStatus(@Param("request_status") RequestStatus request_status);
	
	@Query("select r from Request r join r.account a where r.account = a and a.accountId =:account_id and r.request_status =:request_status order by r.request_id desc")
	public List<Request> getRequestByUserIdAndRequestStatus(@Param("account_id") Integer account_id,@Param("request_status") Integer request_status);
	
	@Query("select r from Request r join r.account a where r.account = a and r.request_status =:request_status order by r.request_id desc")
	public List<Request> getRequestForTaxiByRequestStatus(@Param("request_status") Integer request_status);
	
	
	@Query("select r from Request r , ManageRequest m join r.account a join m.request "
			+ " where r.account = a "
			+ " and m.request = r "
			+ " and a.address = r.place_from "
			+ " and r.request_status =:request_status "
			+ "order by r.request_id desc")
	
	public List<Request> getPendingRequestListTaxi(@Param("request_status") Integer request_status);
	
	
	
}
