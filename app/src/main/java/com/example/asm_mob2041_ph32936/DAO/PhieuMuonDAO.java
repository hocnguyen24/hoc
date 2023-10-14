package com.example.asm_mob2041_ph32936.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041_ph32936.Database.DbHelper;
import com.example.asm_mob2041_ph32936.Model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;

    public PhieuMuonDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",obj.getMaTT());
        contentValues.put("maTV",obj.getMaTV());
        contentValues.put("maSach",obj.getMaSach());
        contentValues.put("ngay",obj.getNgay());
        contentValues.put("tienThue",obj.getTienThue());
        contentValues.put("traSach",obj.getTraSach());

        return db.insert("PhieuMuon",null,contentValues);
    }

    public long update(PhieuMuon obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",obj.getMaTT());
        contentValues.put("maTV",obj.getMaTV());
        contentValues.put("maSach",obj.getMaSach());
        contentValues.put("ngay",obj.getNgay());
        contentValues.put("tienThue",obj.getTienThue());
        contentValues.put("traSach",obj.getTraSach());

        return db.update("PhieuMuon",contentValues,"maPM = ?",new String[]{String.valueOf(obj.getMaPM())});
    }

    public int delete(String id) {
        return db.delete("PhieuMuon","maPM = ?",new String[]{String.valueOf(id)});
    }

    private List<PhieuMuon> getData(String sql, String ... selectionArgs) {
        List<PhieuMuon> lstPM = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstPM.add(new PhieuMuon(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
            ));
        }
        return lstPM;
    }

    public PhieuMuon getID (String id) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPM = ?";
        List<PhieuMuon> lstTV = getData(sql,id);
        return lstTV.get(0);
    }

    public List<PhieuMuon> getAll() {
        String sql = "SELECT * FROM PHIEUMUON";
        return getData(sql);
    }
    public boolean checkID(String id,String value) {
        String Query = "Select * from PHIEUMUON where " + id +  " = " + value;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
