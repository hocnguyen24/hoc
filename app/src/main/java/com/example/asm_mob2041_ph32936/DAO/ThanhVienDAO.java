package com.example.asm_mob2041_ph32936.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041_ph32936.Database.DbHelper;
import com.example.asm_mob2041_ph32936.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("namSinh",obj.getNamSinh());
        contentValues.put("cccd",obj.getCccd());

        return db.insert("ThanhVien",null,contentValues);
    }

    public long update(ThanhVien obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("namSinh",obj.getNamSinh());
        contentValues.put("cccd",obj.getCccd());

        return db.update("ThanhVien",contentValues,"maTV = ?",new String[]{String.valueOf(obj.getMaTV())});
    }

    public int delete(String id) {
        return db.delete("ThanhVien","maTV = ?",new String[]{String.valueOf(id)});
    }

    private List<ThanhVien> getData(String sql, String ... selectionArgs) {
        List<ThanhVien> lstTV = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstTV.add(new ThanhVien(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3)
            ));
        }
        return lstTV;
    }

    public ThanhVien getID (String id) {
        String sql = "SELECT * FROM ThanhVien WHERE maTV = ?";
        List<ThanhVien> lstTV = getData(sql,id);
        return lstTV.get(0);
    }

    public List<ThanhVien> getAll() {
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
    }
 }
