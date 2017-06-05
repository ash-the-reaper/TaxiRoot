package mu.ac.uomtrust.shashi.taximauritius.DTO;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CarDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int carId;
    private String make;
    private int year;
    private int numOfPassenger;
    private String plateNum;
    private byte[] picture1;
    private byte[] picture2;
    private byte[] picture3;
    private byte[] picture4;
    private int accounId;


    public int getAccounId() {
        return accounId;
    }

    public void setAccounId(int accounId) {
        this.accounId = accounId;
    }

    public byte[] getPicture1() {
        return picture1;
    }

    public void setPicture1(byte[] picture1) {
        this.picture1 = picture1;
    }

    public byte[] getPicture2() {
        return picture2;
    }

    public void setPicture2(byte[] picture2) {
        this.picture2 = picture2;
    }

    public byte[] getPicture3() {
        return picture3;
    }

    public void setPicture3(byte[] picture3) {
        this.picture3 = picture3;
    }

    public byte[] getPicture4() {
        return picture4;
    }

    public void setPicture4(byte[] picture4) {
        this.picture4 = picture4;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumOfPassenger() {
        return numOfPassenger;
    }

    public void setNumOfPassenger(int numOfPassenger) {
        this.numOfPassenger = numOfPassenger;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }
}
