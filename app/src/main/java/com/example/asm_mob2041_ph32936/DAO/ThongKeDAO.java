package com.example.asm_mob2041_ph32936.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041_ph32936.Database.DbHelper;
import com.example.asm_mob2041_ph32936.Model.Sach;
import com.example.asm_mob2041_ph32936.Model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public ThongKeDAO (Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Top> getTop () {
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> lstTOP = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = db.rawQuery(sqlTop,null);
        while ((cursor.moveToNext())){
            Sach sach = sachDAO.getID(cursor.getString(0));
            lstTOP.add(new Top(
                    sach.getTenSach(),
                    Integer.parseInt(cursor.getString(1))
            ));
        }
        return lstTOP;
    }

    public int getDoanhThu(String tuNgay,String denNgay) {
        String sqlDoanhthu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> lstDT = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlDoanhthu,new String[]{tuNgay,denNgay});
        while (cursor.moveToNext()) {
            try {
                lstDT.add(Integer.parseInt(cursor.getString(0)));
            } catch (Exception e) {
                lstDT.add(0);
            }
        }
        return lstDT.get(0);
    }
}
