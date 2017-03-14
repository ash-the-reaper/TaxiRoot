package shashi.uomtrust.ac.mu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.entity.Account;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
		
		public Account findById(Long id);
		public Account findByEmail(String email);
		
		@Query("select count(a.email) from Account a where a.email =:email and a.password =:password")
		public int checkAdminLogin(@Param("email")String email, @Param("password")String password);
		
}
