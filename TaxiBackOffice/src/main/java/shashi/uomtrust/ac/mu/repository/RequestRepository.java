package shashi.uomtrust.ac.mu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.entity.Request;



@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {	
	
		
}
