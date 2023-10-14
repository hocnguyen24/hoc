package com.example.asm_mob2041_ph32936.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041_ph32936.Database.DbHelper;
import com.example.asm_mob2041_ph32936.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;

    public SachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Sach obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach",obj.getTenSach());
        contentValues.put("giaThue",obj.getGiaThue());
        contentValues.put("maLoai",obj.getMaLoai());
        contentValues.put("namXuatBan",obj.getNamXuatBan());
        return db.insert("Sach",null,contentValues);
    }

    public long update(Sach obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach",obj.getTenSach());
        contentValues.put("giaThue",obj.getGiaThue());
        contentValues.put("maLoai",obj.getMaLoai());
        contentValues.put("namXuatBan",obj.getNamXuatBan());
        return db.update("Sach",contentValues,"maSach = ?",new String[]{String.valueOf(obj.getMaSach())});
    }

    public int delete(String id) {
        return db.delete("Sach","maSach = ?",new String[]{String.valueOf(id)});
    }

    private List<Sach> getData(String sql, String ... selectionArgs) {
        List<Sach> lstSach = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstSach.add(new Sach(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4))
            ));
        }
        return lstSach;
    }
    public Sach getID (String id) {
        String sql = "SELECT * FROM Sach WHERE maSach = ?";
        List<Sach> lstTT = getData(sql,id);
        return lstTT.get(0);
    }

    public List<Sach> getAll() {
        String sql = "SELECT * FROM SACH";
        return getData(sql);
    }

    public boolean checkID(String fieldValue) {
        String Query = "Select * from SACH where maLoai = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
