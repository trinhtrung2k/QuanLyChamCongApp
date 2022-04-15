package com.trinhtrung.quanlychamcong.models;

import java.io.Serializable;

public class TimekeepingDetailModel implements Serializable {
    private String MACC;
    private String MASP;
    private int SOTP;
    private int SOPP;

    public TimekeepingDetailModel() {
    }

    public TimekeepingDetailModel(String MACC, String MASP) {
        this.MACC = MACC;
        this.MASP = MASP;
    }

    public TimekeepingDetailModel(String MACC, String MASP, int SOTP, int SOPP) {
        this.MACC = MACC;
        this.MASP = MASP;
        this.SOTP = SOTP;
        this.SOPP = SOPP;
    }

    public String getMACC() {
        return MACC;
    }

    public void setMACC(String MACC) {
        this.MACC = MACC;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public int getSOTP() {
        return SOTP;
    }

    public void setSOTP(int SOTP) {
        this.SOTP = SOTP;
    }

    public int getSOPP() {
        return SOPP;
    }

    public void setSOPP(int SOPP) {
        this.SOPP = SOPP;
    }
}
