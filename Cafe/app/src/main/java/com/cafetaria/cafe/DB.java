package com.cafetaria.cafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Smruti on 10/21/2015.
 */
public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "Menu.db";
    public static final String TABLE ="Menu_Table";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="NAME";
    public static final String COL_3 ="PRICE";
    public static final String COL_4 ="DESC";

    public DB(Context context)
    {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE INTEGER, DESC TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        onCreate(db);
    }

     public boolean InsertValues(String name, Integer price , String Desc)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv= new ContentValues();
         cv.put(COL_2,name);
         cv.put(COL_3,price);
         cv.put(COL_4, Desc);
         long result =db.insert(TABLE,null,cv);
         if(result ==1)
             return false;
         else
             return true;

     }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE, null);
        return res;
    }

    public Integer deleteItem(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE, "ID=" + id, null);
    }

   /* public Integer updateItem(String name,String price,String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(COL_1,id);
        val.put(COL_2,name);
        val.put(COL_3,price);
        val.put(COL_4,details);
        return db.update(TABLE, val, "ID = ?", new String[]{id});
    }
*/
    public Cursor getUpdateData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE + " where ID="+id, null);
        return res;
    }


}

