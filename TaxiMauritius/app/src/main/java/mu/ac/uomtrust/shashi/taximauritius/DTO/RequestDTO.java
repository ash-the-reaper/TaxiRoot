package mu.ac.uomtrust.shashi.taximauritius.DTO;

import java.io.Serializable;
import java.util.Date;

import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer requestId;
    private Date dateCreated;
    private Date dateUpdated;
    private Date evenDateTime;
    private String placeFrom;
    private String placeTo;
    private Integer price;
    private RequestStatus requestStatus;
    private Integer accountId;
    private String driverName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Date getEvenDateTime() {
        return evenDateTime;
    }

    public void setEvenDateTime(Date evenDateTime) {
        this.evenDateTime = evenDateTime;
    }

    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        this.placeFrom = placeFrom;
    }

    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String details;


}
