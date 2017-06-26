package shashi.uomtrust.ac.mu.dto;

import java.util.List;

import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;

public class CarDetailsDTO {
	
	private Integer carId;
    private String make;
    private Integer year;
    private Integer numOfPassenger;
    private String plateNum;
    private Integer accountId; 

    private String sPicture1;
    private String sPicture2;
    private String sPicture3;
    private String sPicture4;
    
	public String getsPicture3() {
		return sPicture3;
	}
	public void setsPicture3(String sPicture3) {
		this.sPicture3 = sPicture3;
	}
	public String getsPicture4() {
		return sPicture4;
	}
	public void setsPicture4(String sPicture4) {
		this.sPicture4 = sPicture4;
	}
	public String getsPicture1() {
		return sPicture1;
	}
	public void setsPicture1(String sPicture1) {
		this.sPicture1 = sPicture1;
	}
	public String getsPicture2() {
		return sPicture2;
	}
	public void setsPicture2(String sPicture2) {
		this.sPicture2 = sPicture2;
	}
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
	
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}


  
}