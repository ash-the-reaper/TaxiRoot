package shashi.uomtrust.ac.mu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
		
		public Account findByAccountId(Integer account_id);
		public Account findByEmail(String email);
		
		@Query("select a from Account a where a.userRole =:userRole")
		public List<Account> getAllUserDetails(@Param("userRole") UserRole userRole);
		
		@Query("select a from Account a where a.userRole =:userRole and a.userStatus =:userStatus")
		public List<Account> getAllUserDetailsByStatus(@Param("userStatus") UserStatus userStatus, @Param("userRole") UserRole userRole);
		
		@Query("select count(a.email) from Account a where a.email =:email and a.password =:password")
		public int checkAdminLogin(@Param("email")String email, @Param("password")String password);
		
		@Modifying
		@Query("update Account a set a.userStatus = 1 where a.accountId in (:listAccountId) and a.userRole =:userRole")
		@Transactional
		public void disableClient(@Param("listAccountId") List<Long> listAccountId, @Param("userRole")UserRole userRole);
		
		@Modifying
		@Query("update Account a set a.userStatus = 0 where a.accountId in (:listAccountId) and a.userRole =:userRole")
		@Transactional
		public void enableClient(@Param("listAccountId") List<Long> listAccountId, @Param("userRole")UserRole userRole);
		
		@Modifying
		@Query("delete from Account a where a.accountId in (:listAccountId) and a.userRole =:userRole")
		public void deleteClient(@Param("listAccountId") List<Long> listAccountId, @Param("userRole")UserRole userRole);
		
}
