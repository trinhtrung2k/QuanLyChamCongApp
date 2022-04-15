package com.trinhtrung.quanlychamcong.models;

import java.io.Serializable;

public class SalaryModel implements Serializable {
    private String maCC;
    private String ngayCC;
    private String maSP;
    private String tenSP;
    private int soTP;
    private int soPP;
    private int tienCong;
    private int thanhTien;

    public SalaryModel() {
    }

    public SalaryModel(String maCC, String ngayCC, String maSP) {
        this.maCC = maCC;
        this.ngayCC = ngayCC;
        this.maSP = maSP;
    }

    public SalaryModel(String maCC, String ngayCC, String maSP, String tenSP, int soTP, int soPP, int tienCong) {
        this.maCC = maCC;
        this.ngayCC = ngayCC;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soTP = soTP;
        this.soPP = soPP;
        this.tienCong = tienCong;

    }

    public String getMaCC() {
        return maCC;
    }

    public void setMaCC(String maCC) {
        this.maCC = maCC;
    }

    public String getNgayCC() {
        return ngayCC;
    }

    public void setNgayCC(String ngayCC) {
        this.ngayCC = ngayCC;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoTP() {
        return soTP;
    }

    public void setSoTP(int soTP) {
        this.soTP = soTP;
    }

    public int getSoPP() {
        return soPP;
    }

    public void setSoPP(int soPP) {
        this.soPP = soPP;
    }

    public int getTienCong() {
        return tienCong;
    }

    public void setTienCong(int tienCong) {
        this.tienCong = tienCong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}
