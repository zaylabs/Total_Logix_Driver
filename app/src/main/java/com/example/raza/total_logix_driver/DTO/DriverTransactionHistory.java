package com.example.raza.total_logix_driver.DTO;

import java.util.Date;

public class DriverTransactionHistory {

    private Date transactiondate;
    private String customerID;
    private float amountadded;
    private float oldamount;
    private float newtotal;
    private String transaction;



    public DriverTransactionHistory(){


    }

    public DriverTransactionHistory(Date transactiondate, String customerID, float amountadded,float oldamount, float newtotal, String transaction){


        this.transactiondate = transactiondate;
        this.customerID = customerID;
        this.amountadded = amountadded;
        this.oldamount = oldamount;
        this.newtotal = newtotal;
        this.transaction = transaction;
    }

    public Date getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Date transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public float getAmountadded() {
        return amountadded;
    }

    public void setAmountadded(float amountadded) {
        this.amountadded = amountadded;
    }

    public float getOldamount() {
        return oldamount;
    }

    public void setOldamount(float oldamount) {
        this.oldamount = oldamount;
    }

    public float getNewtotal() {
        return newtotal;
    }

    public void setNewtotal(float newtotal) {
        this.newtotal = newtotal;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
