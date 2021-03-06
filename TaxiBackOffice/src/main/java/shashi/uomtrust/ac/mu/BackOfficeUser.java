package shashi.uomtrust.ac.mu;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import shashi.uomtrust.ac.mu.enums.UserRole;


public class BackOfficeUser extends User {
		
	private static final long serialVersionUID = -1460047265506653280L;

	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	 public BackOfficeUser(Integer id, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities ) {
	    	super(email, password, enabled, true, true, true, authorities);
			this.id = id;
	 }
}