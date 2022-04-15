package com.trinhtrung.quanlychamcong.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String MASP;
    private String TENSP;
    private int DONGIA;

    public ProductModel() {
    }

    public ProductModel(String MASP) {
        this.MASP = MASP;
    }


    public ProductModel(String MASP, String TENSP, int DONGIA) {
        this.MASP = MASP;
        this.TENSP = TENSP;
        this.DONGIA = DONGIA;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public int getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(int DONGIA) {
        this.DONGIA = DONGIA;
    }
}
