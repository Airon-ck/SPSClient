package com.nbxuanma.spsclient.entity;

public class CarBean {

    private int Status;
    private ResultBean Result;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {

        private String license;//车牌
        private String admissiontime;//入场时间
        private String playingtime;//出场时间
        private String parkingtime;//停车时长
        private double orderamount;//订单金额
        private double paid;//已缴费用
        private String Imgcar;//出道口图片

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getAdmissiontime() {
            return admissiontime;
        }

        public void setAdmissiontime(String admissiontime) {
            this.admissiontime = admissiontime;
        }

        public String getPlayingtime() {
            return playingtime;
        }

        public void setPlayingtime(String playingtime) {
            this.playingtime = playingtime;
        }

        public String getParkingtime() {
            return parkingtime;
        }

        public void setParkingtime(String parkingtime) {
            this.parkingtime = parkingtime;
        }

        public double getOrderamount() {
            return orderamount;
        }

        public void setOrderamount(double orderamount) {
            this.orderamount = orderamount;
        }

        public double getPaid() {
            return paid;
        }

        public void setPaid(double paid) {
            this.paid = paid;
        }

        public String getImgcar() {
            return Imgcar;
        }

        public void setImgcar(String imgcar) {
            Imgcar = imgcar;
        }

    }

}
