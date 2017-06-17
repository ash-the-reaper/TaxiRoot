package shashi.uomtrust.ac.mu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087256638192124947L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer request_id;
	
    private int request_status;
    
	@Column(nullable = true)
    private int price;
	
    private String place_from;
    private String place_to;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_created;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_updated;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = true)
	private Account account;

	public Integer getRequest_id() {
		return request_id;
	}


	public void setRequest_id(Integer request_id) {
		this.request_id = request_id;
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


	public String getPlace_from() {
		return place_from;
	}


	public void setPlace_from(String place_from) {
		this.place_from = place_from;
	}


	public String getPlace_to() {
		return place_to;
	}


	public void setPlace_to(String place_to) {
		this.place_to = place_to;
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


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}

}
