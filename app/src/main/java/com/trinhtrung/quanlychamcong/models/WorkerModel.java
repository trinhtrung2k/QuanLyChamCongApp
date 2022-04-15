package com.trinhtrung.quanlychamcong.models;

import java.io.Serializable;

public class WorkerModel implements Serializable {

    private String MACN;
    private String HOCN;
    private String TENCN;
    private int PHANXUONG;


    public WorkerModel() {
    }

    public WorkerModel(String MACN) {
        this.MACN = MACN;
    }

    public WorkerModel(String MACN, String HOCN, String TENCN, int PHANXUONG) {
        this.MACN = MACN;
        this.HOCN = HOCN;
        this.TENCN = TENCN;
        this.PHANXUONG = PHANXUONG;
    }

    public String getMACN() {
        return MACN;
    }

    public void setMACN(String MACN) {
        this.MACN = MACN;
    }

    public String getHOCN() {
        return HOCN;
    }

    public void setHOCN(String HOCN) {
        this.HOCN = HOCN;
    }

    public String getTENCN() {
        return TENCN;
    }

    public void setTENCN(String TENCN) {
        this.TENCN = TENCN;
    }

    public int getPHANXUONG() {
        return PHANXUONG;
    }

    public void setPHANXUONG(int PHANXUONG) {
        this.PHANXUONG = PHANXUONG;
    }

}
