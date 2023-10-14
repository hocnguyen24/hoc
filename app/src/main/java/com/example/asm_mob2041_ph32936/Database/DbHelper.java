package com.example.asm_mob2041_ph32936.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "PNLIB";
    static final int dbVersion = 2;

    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tao bang Thu Thu
        String createTableThuThu =
                "Create table ThuThu (" +
                        "maTT TEXT PRIMARY KEY, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        //Tao bang Thanh Vien
        String createTableThanhVien =
                "Create table ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh INTEGER NOT NULL," +
                        "cccd INTEGER NOT NULL)";
        db.execSQL(createTableThanhVien);

        //Tao bang Phieu Muon
        String createTablePhieuMuon =
                "Create table PhieuMuon(" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maTT TEXT NOT NULL, " +
                        "maTV INTEGER NOT NULL, " +
                        "maSach INTEGER NOT NULL, " +
                        "ngay DATE NOT NULL, " +
                        "tienThue INTEGER NOT NULL, " +
                        "traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);

        //Tao bang Sach
        String createTableSach =
                "Create table Sach(" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "maLoai INTEGER NOT NULL," +
                        "namXuatBan INTEGER NOT NULL )";
        db.execSQL(createTableSach);

        //Tao bang Loai Sach
        String createTableLoaiSach =
                "Create table LoaiSach(" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        String add_pm = "INSERT INTO PhieuMuon VALUES" +
                " (1,'admin',0,0,'23-9-2023',5000,0)";

        db.execSQL(add_pm);


        String add_tt = "INSERT INTO ThuThu VALUES" +
                "('admin','Administrator','admin')," +
                "('ttnva','Nguyễn Văn A','12345')," +
                "('ttnvb','Nguyễn Văn B','12345')";
        db.execSQL(add_tt);

        String add_loaiSach = "INSERT INTO LoaiSach VALUES" +
                "(0,'CNTT')," +
                "(1,'Ngoại ngữ')," +
                "(2,'Sức khỏe')," +
                "(3,'Kinh tế')," +
                "(4,'Khoa học')";
        db.execSQL(add_loaiSach);

        String add_sach = "INSERT INTO Sach VALUES" +
                "(0,'Lập trình Javascrip Cơ bản',5000,0,2000)," +
                "(1,'Lập trình Javascrip Nâng cao',7000,0,2004)," +
                "(2,'Công nghệ tương lai',10000,0,2006)," +
                "(3,'Tri thức về vạn vật',5000,4,1989)," +
                "(4,'Lịch sử khoa học',5000,4,1999)";
        db.execSQL(add_sach);

        String add_thanhvien = "INSERT INTO ThanhVien VALUES" +
                "(0,'Nguyễn Văn A',2000, '0123')," +
                "(1,'Nguyễn Văn B',2001, '2345')," +
                "(2,'Nguyễn Văn C',2002, '3456')," +
                "(3,'Nguyễn Văn D',2003, '4567')," +
                "(4,'Nguyễn Văn E',2004, '5678')";
        db.execSQL(add_thanhvien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableLoaiThuThu = "Drop table if exists ThuThu";
        String dropTableLoaiThanhVien = "Drop table if exists ThanhVien";
        String dropTableLoaiPhieuMuon = "Drop table if exists PhieuMuon";
        String dropTableLoaiSach = "Drop table if exists Sach";
        String dropTableLoaiLoaiSach = "Drop table if exists LoaiSach";

        if (oldVersion != newVersion) {
            db.execSQL(dropTableLoaiThuThu);
            db.execSQL(dropTableLoaiThanhVien);
            db.execSQL(dropTableLoaiPhieuMuon);
            db.execSQL(dropTableLoaiSach);
            db.execSQL(dropTableLoaiLoaiSach);
            onCreate(db);
        }
    }
}
