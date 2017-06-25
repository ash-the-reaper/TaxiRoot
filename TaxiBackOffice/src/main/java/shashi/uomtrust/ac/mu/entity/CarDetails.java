package shashi.uomtrust.ac.mu.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CarDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer carId;
	
    private String make;
    private Integer year;
    private Integer numOfPassenger;
    private String plateNum;
    
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = true)
	private Account account;


	public Integer getCarId() {
		return carId;
	}


	public void setCarId(Integer carId) {
		this.carId = carId;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public Integer getNumOfPassenger() {
		return numOfPassenger;
	}


	public void setNumOfPassenger(Integer numOfPassenger) {
		this.numOfPassenger = numOfPassenger;
	}


	public String getPlateNum() {
		return plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}
}
