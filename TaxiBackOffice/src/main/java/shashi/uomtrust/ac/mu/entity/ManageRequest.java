package shashi.uomtrust.ac.mu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ManageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2076113754656176454L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer manage_request_id;
    
    @Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id", nullable = true)
	private Request request;
	
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = true)
	private Account userAccount;
	
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "car_id", nullable = true)
	private CarDetails carDetails;
    
    private int request_status;
    private int price;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_created;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_updated;
    
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Account getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
	}
	public CarDetails getCarDetails() {
		return carDetails;
	}
	public void setCarDetails(CarDetails carDetails) {
		this.carDetails = carDetails;
	}
	public int getRequest_status() {
		return request_status;
	}
	public void setRequest_status(int request_status) {
		this.request_status = request_status;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public Date getDate_updated() {
		return date_updated;
	}
	public void setDate_updated(Date date_updated) {
		this.date_updated = date_updated;
	}
}
