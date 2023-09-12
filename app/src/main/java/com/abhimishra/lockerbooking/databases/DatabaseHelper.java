package com.abhimishra.lockerbooking.databases;

import static com.abhimishra.lockerbooking.databases.SQLHelper.*;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Data Access Object

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LockerBooking.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_ENTRIES);
        db.execSQL(SQL_CREATE_BOOKING_ENTRIES);
        db.execSQL(SQL_CREATE_LOCKER_ENTRIES);

        //insert Available Lockers
        for (int i = 1; i < 11; i++) {

            String lockerID = "'L" + i + "'";

            String sqlQuery = "INSERT INTO " +
                    DatabaseContract.Locker.TABLE_NAME + " (" +
                    DatabaseContract.Locker.COLUMN_NAME_LOCKER_ID + ", " +
                    DatabaseContract.Locker.COLUMN_NAME_AVAILABILITY + ")" +
                    " VALUES (" + lockerID + ",1); ";
            db.execSQL(sqlQuery);
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USER_ENTRIES);
        db.execSQL(SQL_DELETE_BOOKING_ENTRIES);
        db.execSQL(SQL_DELETE_LOCKER_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
