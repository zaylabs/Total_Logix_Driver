package com.example.raza.total_logix_driver.DTO;

import java.util.Date;

public class overallcash {
    private float cash;
    private float fare;
    private float drivershare;
    private float logixshare;

    private float totaljourney;

    private Date dateupdated;

public overallcash(){}

public overallcash(float cash, float fare, float drivershare, float logixshare, float totaljourney, Date dateupdated){


    this.cash = cash;
    this.fare = fare;
    this.drivershare = drivershare;
    this.logixshare = logixshare;
    this.totaljourney = totaljourney;
    this.dateupdated = dateupdated;
}

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
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

    public float getLogixshare() {
        return logixshare;
    }

    public void setLogixshare(float logixshare) {
        this.logixshare = logixshare;
    }

    public float getTotaljourney() {
        return totaljourney;
    }

    public void setTotaljourney(float totaljourney) {
        this.totaljourney = totaljourney;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }
}

