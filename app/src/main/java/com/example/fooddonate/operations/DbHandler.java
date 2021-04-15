package com.example.fooddonate.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final String MYDB = "users";


    public DbHandler(@Nullable Context context) {
        super(context,MYDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS "+MYDB);
        String query = "CREATE TABLE users(userId TEXT,uName TEXT,phone_no Text,email Text,address Text)";
        Log.d("db","table created");
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void WriteData(String id, String name, String email, String phone_no,String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS "+MYDB);
        ContentValues values = new ContentValues();
        values.put("userId",id );
        values.put("uName",name);
        values.put("phone_no",phone_no);
        values.put("email",email);
        values.put("address",address);
        db.insert(MYDB,null,values);

        db.close();
    }
    public Cursor ReadData(String uId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "select * from "+MYDB +" where userId= '"+uId+"'";
//        Cursor c = MyData.rawQuery("SELECT * FROM " + tableName + " where Category = '" +categoryex + "'" , null);

        Cursor cursor = db.rawQuery(q,null);
        if (cursor.moveToFirst())
        {
            do {
                cursor.getString(0);
            }while (cursor.moveToNext());
//            name = cursor.getString(0);
        }
//        cursor.close();
        db.close();
        return cursor;
    }

}
