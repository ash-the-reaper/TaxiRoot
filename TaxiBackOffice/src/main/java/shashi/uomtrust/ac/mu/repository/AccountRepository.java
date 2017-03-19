package shashi.uomtrust.ac.mu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.enums.UserRole;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
		
		public Account findById(Long id);
		public Account findByEmail(String email);
		
		@Query("select a from Account a where a.role =:userRole")
		public List<Account> getAllUserDetails(@Param("userRole") UserRole userRole);
		
		@Query("select count(a.email) from Account a where a.email =:email and a.password =:password")
		public int checkAdminLogin(@Param("email")String email, @Param("password")String password);		
}
