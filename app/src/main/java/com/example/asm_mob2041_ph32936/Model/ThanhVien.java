package com.example.asm_mob2041_ph32936.Model;

public class ThanhVien {
    private int maTV;
    private String hoTen;
    private int namSinh;

    private String cccd;

    public ThanhVien() {}

    public ThanhVien(int maTV, String hoTen, int namSinh) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public ThanhVien(int maTV, String hoTen, int namSinh, String cccd) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.cccd = cccd;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}
