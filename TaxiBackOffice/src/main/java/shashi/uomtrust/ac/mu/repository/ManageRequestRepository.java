package shashi.uomtrust.ac.mu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.entity.ManageRequest;



@Repository
public interface ManageRequestRepository extends JpaRepository<ManageRequest, Long> {	
	
	
	@Query("select m from ManageRequest m join m.carDetails c where c =:carDetails and m.request_status =:request_status order by m.manage_request_id desc")
	public List<ManageRequest> getManageRequestByStatusForTaxi(@Param("request_status") Integer request_status, @Param("carDetails")CarDetails carDetails);
	
	@Query("select m from ManageRequest m where m.request_status =:request_status and m.userAccount =:account order by m.manage_request_id desc")
	public List<ManageRequest> getManageRequestByStatusForUser(@Param("request_status") Integer request_status, @Param("account")Account account);

	@Query("select m from ManageRequest m where m.carDetails.carId =:carId order by m.manage_request_id desc")
	public List<ManageRequest> getManageRequestForTaxi(@Param("carId")Integer carId);
	
	
	@Query("select m from ManageRequest m join m.request r join r.account a where a =:account and m.request_status =:request_status order by m.manage_request_id desc")
	public List<ManageRequest> getManageRequestForUser(@Param("request_status") Integer request_status, @Param("account")Account account);
	
	
	@Query("select m from ManageRequest m join m.request r join m.carDetails c where c =:carDetails and r.request_id =:requestId order by m.manage_request_id desc")
	public ManageRequest getManageRequestForTaxiByRequestId(@Param("requestId") Integer requestId, @Param("carDetails")CarDetails carDetails);

}
