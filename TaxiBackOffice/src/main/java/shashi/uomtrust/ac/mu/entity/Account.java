package shashi.uomtrust.ac.mu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import shashi.uomtrust.ac.mu.enums.Gender;
import shashi.uomtrust.ac.mu.enums.UserRole;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String firstName;
	
	@Column(length = 100, nullable = false)
	private String lastName;
	
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(nullable = false)
	private UserRole role;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Column(nullable = false)
	private Gender gender;

	public Account() {
	}

	@PrePersist
	public void prePersist() {
		setDateCreated(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", email=" + email + ", role=" + role +"]";
	}
}
