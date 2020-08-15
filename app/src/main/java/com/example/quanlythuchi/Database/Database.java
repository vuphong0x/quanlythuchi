package com.example.quanlythuchi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quanlythuchi.Model.KhoanChi;
import com.example.quanlythuchi.Model.KhoanThu;
import com.example.quanlythuchi.Model.LoaiChi;
import com.example.quanlythuchi.Model.LoaiThu;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    private ContentValues contentValues;
    public Database(@Nullable Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE THU (ID INTEGER PRIMARY KEY, MoTa TEXT, NgayThang TEXT, SoTien INTEGER, LoaiThu TEXT)");
        db.execSQL("CREATE TABLE CHI (ID INTEGER PRIMARY KEY, MoTa TEXT, NgayThang TEXT, SoTien INTEGER, LoaiChi TEXT)");
        db.execSQL("CREATE TABLE LOAITHU (ID INTEGER PRIMARY KEY, LoaiThu TEXT)");
        db.execSQL("CREATE TABLE LOAICHI (ID INTEGER PRIMARY KEY, LoaiChi TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertLoaiThu(LoaiThu loaiThu) {
        contentValues = new ContentValues();
        contentValues.put("LoaiThu", loaiThu.getLoaiThu());
        return sqLiteDatabase.insert("LOAITHU", null, contentValues);
    }

    public long insertLoaiChi(LoaiChi loaiChi) {
        contentValues = new ContentValues();
        contentValues.put("LoaiChi", loaiChi.getLoaiChi());
        return sqLiteDatabase.insert("LOAICHI", null, contentValues);
    }

    public long insertKhoanThu(KhoanThu khoanThu) {
        contentValues = new ContentValues();
        contentValues.put("MoTa", khoanThu.getMoTa());
        contentValues.put("NgayThang", khoanThu.getNgayThang());
        contentValues.put("SoTien", khoanThu.getSoTien());
        contentValues.put("LoaiThu", khoanThu.getLoaiThu());
        return sqLiteDatabase.insert("THU", null, contentValues);
    }

    public long insertKhoanChi(KhoanChi khoanChi) {
        contentValues = new ContentValues();
        contentValues.put("MoTa", khoanChi.getMoTa());
        contentValues.put("NgayThang", khoanChi.getNgayThang());
        contentValues.put("SoTien", khoanChi.getSoTien());
        contentValues.put("LoaiChi", khoanChi.getLoaiChi());
        return sqLiteDatabase.insert("CHI", null, contentValues);
    }

    public long updateLoaiThu(LoaiThu loaiThu, int position) {
        contentValues = new ContentValues();
        contentValues.put("LoaiThu", loaiThu.getLoaiThu());
        return sqLiteDatabase.update("LOAITHU", contentValues, "ID=?", new String[]{String.valueOf(position+1)});
    }

    public long updateLoaiChi(LoaiChi loaiChi, int position) {
        contentValues = new ContentValues();
        contentValues.put("LoaiChi", loaiChi.getLoaiChi());
        return sqLiteDatabase.update("LOAICHI", contentValues, "ID=?", new String[]{String.valueOf(position+1)});
    }

    public long updateKhoanThu(KhoanThu khoanThu, int position) {
        contentValues = new ContentValues();
        contentValues.put("MoTa", khoanThu.getMoTa());
        contentValues.put("NgayThang", khoanThu.getNgayThang());
        contentValues.put("SoTien", khoanThu.getSoTien());
        contentValues.put("LoaiThu", khoanThu.getLoaiThu());
        return sqLiteDatabase.update("THU", contentValues, "ID=?", new String[]{String.valueOf(position+1)});
    }

    public long updateKhoanChi(KhoanChi khoanChi, int position) {
        contentValues = new ContentValues();
        contentValues.put("MoTa", khoanChi.getMoTa());
        contentValues.put("NgayThang", khoanChi.getNgayThang());
        contentValues.put("SoTien", khoanChi.getSoTien());
        contentValues.put("LoaiChi", khoanChi.getLoaiChi());
        return sqLiteDatabase.update("CHI", contentValues, "ID=?", new String[]{String.valueOf(position+1)});
    }

    public List<LoaiThu> getLoaiThu() {
        List<LoaiThu> loaiThuList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAITHU", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                LoaiThu loaiThu = new LoaiThu();
                loaiThu.setLoaiThu(cursor.getString(cursor.getColumnIndex("LoaiThu")));
                loaiThuList.add(loaiThu);
            }
            cursor.close();
        }
        return loaiThuList;
    }

    public List<LoaiChi> getLoaiChi() {
        List<LoaiChi> loaiChiList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAICHI", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                LoaiChi loaiChi = new LoaiChi();
                loaiChi.setLoaiChi(cursor.getString(cursor.getColumnIndex("LoaiChi")));
                loaiChiList.add(loaiChi);
            }
            cursor.close();
        }
        return loaiChiList;
    }

    public List<KhoanThu> getAllKhoanThu() {
        List<KhoanThu> khoanThuList = new ArrayList<>();
        String SELECT = "SELECT * FROM THU";
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                KhoanThu khoanThu = new KhoanThu();
                khoanThu.setMoTa(cursor.getString(cursor.getColumnIndex("MoTa")));
                khoanThu.setNgayThang(cursor.getString(cursor.getColumnIndex("NgayThang")));
                khoanThu.setSoTien(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoTien"))));
                khoanThu.setLoaiThu(cursor.getString(cursor.getColumnIndex("LoaiThu")));
                khoanThuList.add(khoanThu);
            }
            cursor.close();
        }
        return khoanThuList;
    }

    public List<KhoanChi> getAllKhoanChi() {
        List<KhoanChi> khoanChiList = new ArrayList<>();
        String SELECT = "SELECT * FROM CHI";
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                KhoanChi khoanChi = new KhoanChi();
                khoanChi.setMoTa(cursor.getString(cursor.getColumnIndex("MoTa")));
                khoanChi.setNgayThang(cursor.getString(cursor.getColumnIndex("NgayThang")));
                khoanChi.setSoTien(cursor.getInt(cursor.getColumnIndex("SoTien")));
                khoanChi.setLoaiChi(cursor.getString(cursor.getColumnIndex("LoaiChi")));
                khoanChiList.add(khoanChi);
            }
            cursor.close();
        }
        return khoanChiList;
    }

    public int getTongThu () {
        int tongThu = 0;
        String query = "SELECT SoTien FROM THU";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                tongThu += (cursor.getInt(cursor.getColumnIndex("SoTien")));
                cursor.moveToNext();
            }
        }
        return tongThu;
    }

    public int getTongChi () {
        int tongChi = 0;
        String query = "SELECT SoTien FROM CHI";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                tongChi += (cursor.getInt(cursor.getColumnIndex("SoTien")));
                cursor.moveToNext();
            }
        }
        return tongChi;
    }

    public void deleteKhoanThu(String moTa) {
        sqLiteDatabase.delete("THU", "MoTa = ?", new String[]{moTa});
    }

    public void deleteKhoanChi(String moTa) {
        sqLiteDatabase.delete("CHI", "MoTa = ?", new String[]{moTa});
    }

    public void deleteLoaiThu(String loaiThu) {
        sqLiteDatabase.delete("LOAITHU", "LoaiThu = ?" , new String[]{loaiThu});
    }
    public void deleteLoaiChi(String loaiChi) {
        sqLiteDatabase.delete("LOAICHI", "LoaiChi = ?" , new String[]{loaiChi});
    }
}
