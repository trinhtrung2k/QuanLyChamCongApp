package com.trinhtrung.quanlychamcong.models;

import java.io.Serializable;

public class TimekeepingModel implements Serializable {
    String MACC;
    String NGAYCC;
   String MACN;

    public TimekeepingModel() {
    }

    public TimekeepingModel(String MACC) {
        this.MACC = MACC;
    }

    public TimekeepingModel(String MACC, String NGAYCC, String MACN) {
        this.MACC = MACC;
        this.NGAYCC = NGAYCC;
        this.MACN = MACN;
    }

    public String getMACC() {
        return MACC;
    }

    public void setMACC(String MACC) {
        this.MACC = MACC;
    }

    public String getNGAYCC() {
        return NGAYCC;
    }

    public void setNGAYCC(String NGAYCC) {
        this.NGAYCC = NGAYCC;
    }

    public String getMACN() {
        return MACN;
    }

    public void setMACN(String MACN) {
        this.MACN = MACN;
    }
}
