package com.example.shopapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.util.Log;

import java.sql.Blob;

public class TemporaryDatabase  extends SQLiteOpenHelper  {
    public static final String DATABASE_NAME = "temp.db";
    public static final String TABLE_NAME = "torder";
    public static final String ITEMID = "ItemId";
    public static final String ITEMNAME = "Itemname";
    public static final String QTY = "Qty";
    public static final String DISPRICE = "DiscountPrice";
    public static final String PRICE = "Price";
    public static final String UNIT = "Unit";
    public static final String AMOUNT = "Amount";

    public static Context context;



    public TemporaryDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    //private static String FIRST_DB_PATH = context.getDatabasePath("shop.db").toString();

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_NEW_SHOP = "CREATE TABLE " + TABLE_NAME + "(ItemId Integer,  Itemname Text,  Qty INTEGER, Price INTEGER , DiscountPrice INTEGER, Unit Text, Amount Integer , Image BLOB)";
        db.execSQL(CREATE_TABLE_NEW_SHOP);
        Log.d("SQLMessage", "TABLE WAS CREATED ");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Shop_TABLE");
        onCreate(db);
    }

    public boolean insertData(Integer itemid , String itemname, String qty, Integer Price , Integer DiscountPrice , String Unit , Integer Amount ,byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEMID, itemid);
        values.put(ITEMNAME, itemname);
        values.put(QTY, qty);
        values.put(PRICE, Price);
        values.put(DISPRICE,DiscountPrice);
        values.put(UNIT,Unit);
        values.put(AMOUNT,Amount);
        values.put("Image", image);
        Log.d("sql","data inserted in table 2");


        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor gettemporder()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Log.d("sql","getorder executed");
        String[] columns = new String[] { ITEMID, ITEMNAME,QTY,PRICE ,DISPRICE , UNIT , AMOUNT,"Image"};
        String selectQuery = "SELECT  ItemId , Itemname, Qty , Price , DiscountPrice , Unit , Amount ,Image FROM " + TABLE_NAME ;
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean cleardata(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        return true;
    }
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ItemId = ?", new String[]{id});

    }
}