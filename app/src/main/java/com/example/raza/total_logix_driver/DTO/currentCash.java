package com.example.raza.total_logix_driver.DTO;

import java.util.Date;

public class currentCash {

    private float currentcash;
    private float totalfarepending;
    private float totaldriversharepending;
    private float totlalogixsharepending;

    private float totaljourney;

    private Date dateupdated;

public currentCash(){}

public currentCash(float currentcash, float totalfarepending, float totaldriversharepending, float totlalogixsharepending, float totaljourney, Date dateupdated){


    this.currentcash = currentcash;
    this.totalfarepending = totalfarepending;
    this.totaldriversharepending = totaldriversharepending;
    this.totlalogixsharepending = totlalogixsharepending;
    this.totaljourney = totaljourney;
    this.dateupdated = dateupdated;
}

    public float getCurrentcash() {
        return currentcash;
    }

    public void setCurrentcash(float currentcash) {
        this.currentcash = currentcash;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public float getTotalfarepending() {
        return totalfarepending;
    }

    public void setTotalfarepending(float totalfarepending) {
        this.totalfarepending = totalfarepending;
    }

    public float getTotaldriversharepending() {
        return totaldriversharepending;
    }

    public void setTotaldriversharepending(float totaldriversharepending) {
        this.totaldriversharepending = totaldriversharepending;
    }

    public float getTotlalogixsharepending() {
        return totlalogixsharepending;
    }

    public void setTotlalogixsharepending(float totlalogixsharepending) {
        this.totlalogixsharepending = totlalogixsharepending;
    }

    public float getTotaljourney() {
        return totaljourney;
    }

    public void setTotaljourney(float totaljourney) {
        this.totaljourney = totaljourney;
    }
}
