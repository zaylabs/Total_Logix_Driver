package com.example.raza.total_logix_driver.DTO;

import java.util.Date;

public class settlementHistory {

    private String totallogixemployee;
    private String driverID;
    private float totalfare;
    private float drivershare;
    private float totallogixshare;
    private float driverpayment;
    private float totalcash;
    private Date settlementdate;

    public settlementHistory(){}

    public settlementHistory(String totallogixemployee,String driverID,float totalfare, float drivershare, float totallogixshare, float driverpayment,float totalcash,Date settlementdate){
        this.totallogixemployee = totallogixemployee;
        this.driverID = driverID;


        this.totalfare = totalfare;
        this.drivershare = drivershare;
        this.totallogixshare = totallogixshare;
        this.driverpayment = driverpayment;
        this.totalcash = totalcash;
        this.settlementdate = settlementdate;
    }

    public float getTotalfare() {
        return totalfare;
    }

    public void setTotalfare(float totalfare) {
        this.totalfare = totalfare;
    }

    public float getDrivershare() {
        return drivershare;
    }

    public void setDrivershare(float drivershare) {
        this.drivershare = drivershare;
    }

    public float getTotallogixshare() {
        return totallogixshare;
    }

    public void setTotallogixshare(float totallogixshare) {
        this.totallogixshare = totallogixshare;
    }

    public float getDriverpayment() {
        return driverpayment;
    }

    public void setDriverpayment(float driverpayment) {
        this.driverpayment = driverpayment;
    }

    public Date getSettlementdate() {
        return settlementdate;
    }

    public void setSettlementdate(Date settlementdate) {
        this.settlementdate = settlementdate;
    }

    public float getTotalcash() {
        return totalcash;
    }

    public void setTotalcash(float totalcash) {
        this.totalcash = totalcash;
    }

    public String getTotallogixemployee() {
        return totallogixemployee;
    }

    public void setTotallogixemployee(String totallogixemployee) {
        this.totallogixemployee = totallogixemployee;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }
}
