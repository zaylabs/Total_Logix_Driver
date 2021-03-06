package com.example.raza.total_logix_driver.DTO;

import android.net.Uri;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class driverHistory {


    private String name;
    private String phone;
    private GeoPoint originalpickup;
    private GeoPoint originaldrop;
    private GeoPoint actualpickup;
    private GeoPoint actualdrop;
    private Date date;
    private String CID;
    private String VT;
    private String weight;
    private String boxes;
    private String description;
    private String driverloading;
    private float ridedistance;
    private String pickupaddress;
    private String dropaddress;
    private String estFare;
    private String drivername;
    private String driverdp;
    private String drivernic;
    private String driverphone;
    private GeoPoint driverlocation;
    private String carregno;
    private String driverid;
    private String status;
    private float ridefare;
    private String paidvia;
    private String paymentstatus;
    private float waitingtime;
    private String uniqueID;
    private String settlement;
    private float driverridestars;
    private float customerridestars;
    private float estDistance;
    private String gatepass;
    private Date arriveddate;
    private String droplocationUniqueID;
    driverHistory(){

        }

        public driverHistory (String name, GeoPoint originalpickup, GeoPoint originaldrop, GeoPoint actualpickup, GeoPoint actualdrop, String phone, Date date, String CID, String VT, String weight, String boxes , String description, String driverloading, float ridedistance, String pickupaddress, String dropaddress, String estFare, String drivername, String driverdp, String drivernic, String driverphone, GeoPoint driverlocation, String carregno, String driverid, String status, float ridefare, String paidvia, String paymentstatus, float waitingtime, String uniqueID, String settlement, float driverridestars,float customerridestars, float estDistance, String gatepass, Date arriveddate,String droplocationUniqueID){

            this.name = name;
            this.originalpickup = originalpickup;
            this.originaldrop = originaldrop;
            this.actualpickup=actualpickup;
            this.actualdrop=actualdrop;
            this.phone = phone;
            this.date =date;
            this.CID = CID;
            this.VT = VT;
            this.weight=weight;
            this.boxes=boxes;
            this.description=description;
            this.driverloading=driverloading;
            this.ridedistance=ridedistance;
            this.pickupaddress=pickupaddress;
            this.dropaddress=dropaddress;
            this.estFare=estFare;
            this.drivername=drivername;
            this.driverdp=driverdp;
            this.drivernic=drivernic;
            this.driverphone=driverphone;
            this.driverlocation=driverlocation;
            this.carregno=carregno;
            this.driverid=driverid;
            this.status=status;
            this.ridefare=ridefare;
            this.paidvia=paidvia;
            this.paymentstatus=paymentstatus;
            this.uniqueID=uniqueID;
            this.waitingtime=waitingtime;
            this.settlement=settlement;
            this.driverridestars = driverridestars;
            this.customerridestars = customerridestars;

            this.estDistance=estDistance;
            this.gatepass=gatepass;
            this.arriveddate = arriveddate;
            this.droplocationUniqueID = droplocationUniqueID;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getVT() {
        return VT;
    }

    public void setVT(String VT) {
        this.VT = VT;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBoxes() {
        return boxes;
    }

    public void setBoxes(String boxes) {
        this.boxes = boxes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDriverloading() {
        return driverloading;
    }

    public void setDriverloading(String driverloading) {
        this.driverloading = driverloading;
    }

    public float getRidedistance() {
        return ridedistance;
    }

    public void setRidedistance(float ridedistance) {
        this.ridedistance = ridedistance;
    }

    public float getWaitingtime() {
        return waitingtime;
    }

    public void setWaitingtime(float waitingtime) {
        this.waitingtime = waitingtime;
    }

    public String getPickupaddress() {
        return pickupaddress;
    }

    public void setPickupaddress(String pickupaddress) {
        this.pickupaddress = pickupaddress;
    }

    public String getDropaddress() {
        return dropaddress;
    }

    public void setDropaddress(String dropaddress) {
        this.dropaddress = dropaddress;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDriverdp() {
        return driverdp;
    }

    public void setDriverdp(String driverdp) {
        this.driverdp = driverdp;
    }

    public String getDrivernic() {
        return drivernic;
    }

    public void setDrivernic(String drivernic) {
        this.drivernic = drivernic;
    }

    public String getDriverphone() {
        return driverphone;
    }

    public void setDriverphone(String driverphone) {
        this.driverphone = driverphone;
    }

    public GeoPoint getDriverlocation() {
        return driverlocation;
    }

    public void setDriverlocation(GeoPoint driverlocation) {
        this.driverlocation = driverlocation;
    }

    public String getCarregno() {
        return carregno;
    }

    public void setCarregno(String carregno) {
        this.carregno = carregno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public float getRidefare() {
        return ridefare;
    }

    public void setRidefare(float ridefare) {
        this.ridefare = ridefare;
    }

    public String getPaidvia() {
        return paidvia;
    }

    public void setPaidvia(String paidvia) {
        this.paidvia = paidvia;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getEstFare() {
        return estFare;
    }

    public void setEstFare(String estFare) {
        this.estFare = estFare;
    }

    public GeoPoint getOriginalpickup() {
        return originalpickup;
    }

    public void setOriginalpickup(GeoPoint originalpickup) {
        this.originalpickup = originalpickup;
    }

    public GeoPoint getOriginaldrop() {
        return originaldrop;
    }

    public void setOriginaldrop(GeoPoint originaldrop) {
        this.originaldrop = originaldrop;
    }

    public GeoPoint getActualpickup() {
        return actualpickup;
    }

    public void setActualpickup(GeoPoint actualpickup) {
        this.actualpickup = actualpickup;
    }

    public GeoPoint getActualdrop() {
        return actualdrop;
    }

    public void setActualdrop(GeoPoint actualdrop) {
        this.actualdrop = actualdrop;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public float getDriverridestars() {
        return driverridestars;
    }

    public void setDriverridestars(float driverridestars) {
        this.driverridestars = driverridestars;
    }

    public float getCustomerridestars() {
        return customerridestars;
    }

    public void setCustomerridestars(float customerridestars) {
        this.customerridestars = customerridestars;
    }

    public float getEstDistance() {
        return estDistance;
    }

    public void setEstDistance(float estDistance) {
        this.estDistance = estDistance;
    }

    public String getGatepass() {
        return gatepass;
    }

    public void setGatepass(String gatepass) {
        this.gatepass = gatepass;
    }

    public Date getArriveddate() {
        return arriveddate;
    }

    public void setArriveddate(Date arriveddate) {
        this.arriveddate = arriveddate;
    }

    public String getDroplocationUniqueID() {
        return droplocationUniqueID;
    }

    public void setDroplocationUniqueID(String droplocationUniqueID) {
        this.droplocationUniqueID = droplocationUniqueID;
    }
}




