package com.example.asm_mob2041_ph32936.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041_ph32936.Database.DbHelper;
import com.example.asm_mob2041_ph32936.Model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",obj.getMaTT());
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("matKhau",obj.getMatKhau());

        return db.insert("ThuThu",null,contentValues);
    }

    public long update(ThuThu obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("matKhau",obj.getMatKhau());

        return db.update("ThuThu",contentValues,"maTT = ?",new String[]{String.valueOf(obj.getMaTT())});
    }

    public int delete(String id) {
        return db.delete("ThuThu","maTT = ?",new String[]{String.valueOf(id)});
    }

    private List<ThuThu> getData(String sql, String ... selectionArgs) {
        List<ThuThu> lstTT = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstTT.add(new ThuThu(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            ));
        }
        return lstTT;
    }

    public ThuThu getID (String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ?";
        List<ThuThu> lstTT = getData(sql,id);
        return lstTT.get(0);
    }

    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM THUTHU";
        return getData(sql);
    }

    public long checkLogin(String username,String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?",new String[]{username,password});
        if (cursor.getCount() > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
