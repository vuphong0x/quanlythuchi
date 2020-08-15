package com.example.quanlythuchi.Model;

import java.util.Random;

public class KhoanThu {
    private String MoTa;
    private String NgayThang;
    private int SoTien;
    private String LoaiThu;

    public KhoanThu() {
    }

    public KhoanThu(String moTa, String ngayThang, int soTien, String loaiThu) {
        MoTa = moTa;
        NgayThang = ngayThang;
        SoTien = soTien;
        LoaiThu = loaiThu;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getNgayThang() {
        return NgayThang;
    }

    public void setNgayThang(String ngayThang) {
        NgayThang = ngayThang;
    }

    public int getSoTien() {
        return SoTien;
    }

    public void setSoTien(int soTien) {
        SoTien = soTien;
    }

    public String getLoaiThu() {
        return LoaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        LoaiThu = loaiThu;
    }
}
