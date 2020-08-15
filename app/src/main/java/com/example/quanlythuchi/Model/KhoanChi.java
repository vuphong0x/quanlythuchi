package com.example.quanlythuchi.Model;

public class KhoanChi {
    private String MoTa;
    private String NgayThang;
    private int SoTien;
    private String LoaiChi;

    public KhoanChi() {
    }

    public KhoanChi(String moTa, String ngayThang, int soTien, String loaiChi) {
        MoTa = moTa;
        NgayThang = ngayThang;
        SoTien = soTien;
        LoaiChi = loaiChi;
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

    public String getLoaiChi() {
        return LoaiChi;
    }

    public void setLoaiChi(String loaiChi) {
        LoaiChi = loaiChi;
    }
}
