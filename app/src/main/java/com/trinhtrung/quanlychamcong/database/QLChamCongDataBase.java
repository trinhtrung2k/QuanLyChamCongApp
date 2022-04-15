package com.trinhtrung.quanlychamcong.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingDetailModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.ArrayList;

public class QLChamCongDataBase extends SQLiteOpenHelper {
    private static String DB_NAME= "QuanLyChamCong.db";
    private static int DB_VERSION = 11;


    //table congnhan
    private static final String TB_CONGNHAN = "CONGNHAN";
    private static final String COL_CONGNHAN_ID = "ID";
    private static final String COL_CONGNHAN_MACN = "MACN";
    private static final String  COL_CONGNHAN_HOCN = "HOCN";
    private static final String  COL_CONGNHAN_TENCN = "TENCN";
    private static final String  COL_CONGNHAN_PHANXUONG = "PHANXUONG";

    //table sanpham
    private static final String TB_SANPHAM = "SANPHAM";
    private static final String COL_SANPHAM_ID = "ID";
    private static final String COL_SANPHAM_MASP = "MASP";
    private static final String  COL_SANPHAM_TENSP = "TENSP";
    private static final String  COL_SANPHAM_DONGIA = "DONGIA";

    //table chamcong
    private static final String TB_CHAMCONG = "CHAMCONG";
    private static final String COL_CHAMCONG_ID = "ID";
    private static final String COL_CHAMCONG_MACC = "MACC";
    private static final String  COL_CHAMCONG_NGAYCC = "NGAYCC";
    private static final String  COL_CHAMCONG_MACN = "MACN";

    //table chitietchamcong
    private static final String TB_CTCHAMCONG = "CT_CHAMCONG";
    private static final String COL_CTCHAMCONG_MACC = "MACC";
    private static final String  COL_CTCHAMCONG_MASP = "MASP";
    private static final String  COL_CTCHAMCONG_SOTP = "SOTP";
    private static final String  COL_CTCHAMCONG_SOPP = "SOPP";

    public QLChamCongDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       /* String scriptTBCongNhan =  " CREATE TABLE " + TB_CONGNHAN + "(" + COL_CONGNHAN_MACN + " TEXT PRIMARY KEY , "  + COL_CONGNHAN_HOCN + " TEXT NOT NULL," +
                COL_CONGNHAN_TENCN + " TEXT NOT NULL, " +COL_CONGNHAN_PHANXUONG + " TEXT NOT NULL)";

        String scriptTBSanPham =  " CREATE TABLE " + TB_SANPHAM + "(" + COL_SANPHAM_MASP + " TEXT PRIMARY KEY , " + COL_SANPHAM_TENSP + " TEXT NOT NULL," +
                COL_SANPHAM_DONGIA + " INTEGER NOT NULL)";

        String scriptTBChamCong =  " CREATE TABLE " + TB_CHAMCONG + "("+  COL_CHAMCONG_MACC + " TEXT PRIMARY KEY, " + COL_CHAMCONG_NGAYCC + " TEXT NOT NULL, " +
                 "  MACN TEXT NOT NULL REFERENCES CONGNHAN (MACN))";

        String scriptTBCTChamCong =  " CREATE TABLE " + TB_CTCHAMCONG + "(" + "MACC TEXT NOT NULL REFERENCES CHAMCONG (MACC), MASP TEXT NOT NULL REFERENCES SANPHAM (MASP), "   +
                COL_CTCHAMCONG_SOTP+ " INTEGER NOT NULL, " + COL_CTCHAMCONG_SOPP + " INTEGER NOT NULL)";*/

        String scriptTBCongNhan =  " CREATE TABLE " + TB_CONGNHAN + "(" + COL_CONGNHAN_MACN + " TEXT PRIMARY KEY , "  + COL_CONGNHAN_HOCN + " TEXT NOT NULL," +
                COL_CONGNHAN_TENCN + " TEXT NOT NULL, " +COL_CONGNHAN_PHANXUONG + " TEXT NOT NULL)";

        String scriptTBSanPham =  " CREATE TABLE " + TB_SANPHAM + "(" + COL_SANPHAM_MASP + " TEXT PRIMARY KEY , " + COL_SANPHAM_TENSP + " TEXT NOT NULL," +
                COL_SANPHAM_DONGIA + " INTEGER NOT NULL)";

        String scriptTBChamCong =  " CREATE TABLE " + TB_CHAMCONG + "("+  COL_CHAMCONG_MACC + " TEXT PRIMARY KEY, " + COL_CHAMCONG_NGAYCC + " TEXT NOT NULL, " +
                "  MACN TEXT NOT NULL, " + " FOREIGN KEY (MACN) REFERENCES CONGNHAN (MACN) \n ON UPDATE RESTRICT\n" +
                "       ON DELETE RESTRICT)  ";

        String scriptTBCTChamCong =  " CREATE TABLE " + TB_CTCHAMCONG + "(" + "MACC TEXT NOT NULL , MASP TEXT NOT NULL, "   +
                COL_CTCHAMCONG_SOTP+ " INTEGER NOT NULL, " + COL_CTCHAMCONG_SOPP + " INTEGER NOT NULL, " + " FOREIGN KEY (MACC) REFERENCES CHAMCONG (MACC) \nON UPDATE RESTRICT\n" +
                "       ON DELETE RESTRICT, " + " FOREIGN KEY (MASP) REFERENCES SANPHAM (MASP) \nON UPDATE RESTRICT\n" +
                "       ON DELETE RESTRICT)";
        db.execSQL(scriptTBCongNhan);
        db.execSQL(scriptTBSanPham);
        db.execSQL(scriptTBChamCong);
        db.execSQL(scriptTBCTChamCong);
        String query = String.format ("PRAGMA foreign_keys = %s","ON");
        db.execSQL(query);
     //   db.setForeignKeyConstraintsEnabled(true);

    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_CONGNHAN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_SANPHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CHAMCONG);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CTCHAMCONG);
    //    db.execSQL("PRAGMA foreign_keys = ON;");

        onCreate(db);
    }

    // WORKER
    public void AddWorker(WorkerModel workerModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Insert into CONGNHAN values(?,?,?,?)";
        db.execSQL(query,new Object[]{workerModel.getMACN(),workerModel.getHOCN(), workerModel.getTENCN(), workerModel.getPHANXUONG()});
    }
    public void EditWorker(WorkerModel workerModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "update CONGNHAN set HOCN =?, TENCN=?, PHANXUONG=? where MACN=?";
        db.execSQL(query,new Object[]{workerModel.getHOCN(), workerModel.getTENCN(), workerModel.getPHANXUONG(),workerModel.getMACN()});
    }

    public void DeleteWorker(WorkerModel workerModel)
    {
        SQLiteDatabase database = getWritableDatabase();
        String query="delete from CONGNHAN where MACN=?";
        database.execSQL(query, new Object[]{workerModel.getMACN()});
    }


    // PRODUCT
    public void AddProduct(ProductModel productModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Insert into SANPHAM values(?,?,?)";
        db.execSQL(query,new Object[]{productModel.getMASP(),productModel.getTENSP(), productModel.getDONGIA()});
    }
    public void EditProduct(ProductModel productModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "update SANPHAM set TENSP=?, DONGIA=? where  MASP =?";
        db.execSQL(query,new Object[]{productModel.getTENSP(), productModel.getDONGIA(), productModel.getMASP()});
    }

    public void DeleteProduct(ProductModel productModel)
    {
        SQLiteDatabase database = getWritableDatabase();
        String query="delete from SANPHAM where MASP=?";
        database.execSQL(query, new Object[]{productModel.getMASP()});
    }


    // TIMEKEEPING
    public void AddTimekeeping(TimekeepingModel timekeepingModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Insert into CHAMCONG values(?,?,?)";
        db.execSQL(query,new Object[]{timekeepingModel.getMACC(),timekeepingModel.getNGAYCC(), timekeepingModel.getMACN()});
    }
    public void EditTimekeeping(TimekeepingModel timekeepingModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "update CHAMCONG set NGAYCC=? where  MACC =? AND MACN =?";
        db.execSQL(query,new Object[]{timekeepingModel.getNGAYCC(),timekeepingModel.getMACC(),timekeepingModel.getMACN()});
    }

    public void DeleteTimekeeping(TimekeepingModel timekeepingModel)
    {
        SQLiteDatabase database = getWritableDatabase();
        String query="delete from CHAMCONG where MACC=?";
        database.execSQL(query, new Object[]{timekeepingModel.getMACC()});
    }

    public void AddTimekeepingDetail(TimekeepingDetailModel timekeepingDetailModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Insert into CT_CHAMCONG values(?,?,?,?)";
        db.execSQL(query,new Object[]{timekeepingDetailModel.getMACC(),timekeepingDetailModel.getMASP(), timekeepingDetailModel.getSOTP(),timekeepingDetailModel.getSOPP()});
    }
    public void EditTimekeepingDetail(TimekeepingDetailModel timekeepingDetailModel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "update CT_CHAMCONG set SOTP=?, SOPP=? where  MACC =? AND MASP = ?";
        db.execSQL(query,new Object[]{timekeepingDetailModel.getSOTP(), timekeepingDetailModel.getSOPP(), timekeepingDetailModel.getMACC(),timekeepingDetailModel.getMASP()});
    }

    public void DeleteTimekeepingDetail(TimekeepingDetailModel timekeepingDetailModel)
    {
        SQLiteDatabase database = getWritableDatabase();
        String query="delete from CT_CHAMCONG where MACC=? AND MASP=?";
        database.execSQL(query, new Object[]{timekeepingDetailModel.getMACC(),timekeepingDetailModel.getMASP()});
    }
    public void CheckTimekeepingDetail(TimekeepingDetailModel timekeepingDetailModel)
    {
        SQLiteDatabase database = getWritableDatabase();
        String query="select 1 from CHAMCONG, SANPHAM where CHAMCONG.MACC=? AND SANPHAM.MASP=?";
        database.execSQL(query, new Object[]{timekeepingDetailModel.getMACC(),timekeepingDetailModel.getMASP()});
    }


    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return  database.rawQuery(sql,null);
    }

}
