package com.example.raza.total_logix_driver.DTO;

import java.util.Date;

public class fareDistribution {


    private float fare;
    private float drivershare;
    private float totallogixshare;
    private String logixid;
    private Date date;

    fareDistribution(){}

    fareDistribution(float fare,float drivershare, float totallogixshare, String logixid,Date date){


        this.fare = fare;
        this.drivershare = drivershare;
        this.totallogixshare = totallogixshare;
        this.logixid = logixid;
        this.date = date;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
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

    public String getLogixid() {
        return logixid;
    }

    public void setLogixid(String logixid) {
        this.logixid = logixid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
