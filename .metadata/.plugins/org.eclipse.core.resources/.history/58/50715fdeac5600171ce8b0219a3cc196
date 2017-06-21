package shashi.uomtrust.ac.mu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.ManageRequest;



@Repository
public interface ManageRequestRepository extends JpaRepository<ManageRequest, Long> {	
	
	
	@Query("select m from ManageRequest m where m.request_status =:request_status and m.userAccount =:account")
	public List<ManageRequest> getManageRequestByStatusForTaxi(@Param("request_status") Integer request_status, @Param("account")Account account);

	@Query("select m from ManageRequest m where m.userAccount =:account")
	public List<ManageRequest> getManageRequestForTaxi(@Param("account")Account account);

}
