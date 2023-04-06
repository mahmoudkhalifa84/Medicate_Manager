package com.medicate.medicatemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;



public class NotificationsDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "data.db";
    public static String NOTIFICATIONS_TABLE = "NOTI";
    public static String TAG = "NOTIFICATIONS_TAG";
    public static String BODY = "body";
    public static String DATE = "date";
    public static String STATE = "state";
    Context context;
    private static final String SQL_NOTIFICATIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS NOTI ( id INTEGER PRIMARY KEY AUTOINCREMENT , body TEXT , date TEXT , state TEXT)";

    Statics statics;
    public NotificationsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context =  context;
        Create();
        statics = new Statics(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_NOTIFICATIONS_TABLE);
    }
    public void Create(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_NOTIFICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NOTI");
        onCreate(db);
    }
    public void SetStateToOld(){
        SQLiteDatabase db =  this.getReadableDatabase();
        // 0 old
        // 1 new
        db.execSQL(("UPDATE NOTI SET state = '0'"));


    }
    public Cursor getData(){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from NOTI",null);
        return res;
    }

    public int getNewCount(){
        if (statics.getID().equals("null"))
            return 0;
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from NOTI where state = " + "1",null);
        return res.getCount();
    }
    public boolean insertData(String body,String date){
        long resa = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BODY, body);
            contentValues.put(DATE, date);
            contentValues.put(STATE, "1");
            resa = db.insert("NOTI", null, contentValues);
            db.close();
        } catch (SQLException e){
            return false;
        }
        if (resa == -1) {
            Log.d(TAG, "insertData: FALSE");
            return false;
        }
        else {
            Log.d(TAG, "insertData: TRUE");
            return true;

        }
    }

    public void DelDate() {
        SQLiteDatabase db =  this.getReadableDatabase();
        db.rawQuery("delete from NOTI",null).close();
    }
}
