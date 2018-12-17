package com.nbxuanma.spsclient.entity;

public class CarBean {

    /**
     * License : 浙B12345
     * Admissiontime : 2018-12-17 10:36:01
     * Playingtime : 2018-12-17 11:41:41
     * Parkingtime : 01小时05分
     * Orderamount : 0.10
     * Paid : 0
     * Status : 0
     * Imgcar : null
     */

    private String License;
    private String Admissiontime;
    private String Playingtime;
    private String Parkingtime;
    private String Orderamount;
    private String Paid;
    private int Status;
    private String Imgcar;

    public String getLicense() {
        return License;
    }

    public void setLicense(String License) {
        this.License = License;
    }

    public String getAdmissiontime() {
        return Admissiontime;
    }

    public void setAdmissiontime(String Admissiontime) {
        this.Admissiontime = Admissiontime;
    }

    public String getPlayingtime() {
        return Playingtime;
    }

    public void setPlayingtime(String Playingtime) {
        this.Playingtime = Playingtime;
    }

    public String getParkingtime() {
        return Parkingtime;
    }

    public void setParkingtime(String Parkingtime) {
        this.Parkingtime = Parkingtime;
    }

    public String getOrderamount() {
        return Orderamount;
    }

    public void setOrderamount(String Orderamount) {
        this.Orderamount = Orderamount;
    }

    public String getPaid() {
        return Paid;
    }

    public void setPaid(String Paid) {
        this.Paid = Paid;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getImgcar() {
        return Imgcar;
    }

    public void setImgcar(String Imgcar) {
        this.Imgcar = Imgcar;
    }

}
