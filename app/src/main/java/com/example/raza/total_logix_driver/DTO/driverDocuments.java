package com.example.raza.total_logix_driver.DTO;

import java.util.Date;

public class driverDocuments {

    private String dl_front;
    private String dl_back;
    private String cnic_front;
    private String cnic_back;
    private String car_reg_doc;
    private Date docUploadDate;

    public driverDocuments(){

    }

    public driverDocuments(String dl_front, String dl_back, String cnic_front, String cnic_back, String car_reg_doc, Date  docUploadDate){

        this.dl_front = dl_front;
        this.dl_back = dl_back;
        this.cnic_front = cnic_front;
        this.cnic_back = cnic_back;
        this.car_reg_doc = car_reg_doc;
        this.docUploadDate = docUploadDate;
    }
    public String getDl_front() {
        return dl_front;
    }

    public void setDl_front(String dl_front) {
        this.dl_front = dl_front;
    }

    public String getDl_back() {
        return dl_back;
    }

    public void setDl_back(String dl_back) {
        this.dl_back = dl_back;
    }

    public String getCnic_front() {
        return cnic_front;
    }

    public void setCnic_front(String cnic_front) {
        this.cnic_front = cnic_front;
    }

    public String getCnic_back() {
        return cnic_back;
    }

    public void setCnic_back(String cnic_back) {
        this.cnic_back = cnic_back;
    }

    public String getCar_reg_doc() {
        return car_reg_doc;
    }

    public void setCar_reg_doc(String car_reg_doc) {
        this.car_reg_doc = car_reg_doc;
    }

    public Date getDocUploadDate() {
        return docUploadDate;
    }

    public void setDocUploadDate(Date docUploadDate) {
        this.docUploadDate = docUploadDate;
    }
}
