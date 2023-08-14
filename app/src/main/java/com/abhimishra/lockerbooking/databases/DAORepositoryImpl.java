package com.abhimishra.lockerbooking.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAORepositoryImpl implements DAORepository {

    SQLiteDatabase db;

    public DAORepositoryImpl(Context context) {

        // initialize the db
        DatabaseHelper dbHelper = new DatabaseHelper(context);// getContext() is replaced by getBaseContext()
        db = dbHelper.getWritableDatabase();
        db = dbHelper.getReadableDatabase();
    }


    @Override
    public void insert(Map<String, String> map, String tableName) {

        ContentValues values = new ContentValues();
        // TODO - Refactor the logic to include only required columns to be added

        if (tableName == DatabaseContract.User.TABLE_NAME) {

            values.put(DatabaseContract.User.COLUMN_NAME_MOBILE_NUMBER, map.get("MOBILE_NUMBER"));
            values.put(DatabaseContract.User.COLUMN_NAME_REFERENCE_ID, map.get("REF_ID"));
            values.put(DatabaseContract.User.COLUMN_NAME_PAYMENT_DETAILS, map.get("PAYMENT_DETAILS"));
            values.put(DatabaseContract.User.COLUMN_NAME_LIST_OF_BOOKINGS, map.get("LIST_OF_BOOKING"));

            db.insert(DatabaseContract.User.TABLE_NAME, null, values);

        } else if (tableName == DatabaseContract.Booking.TABLE_NAME) {

            values.put(DatabaseContract.Booking.COLUMN_NAME_BOOKING_ID, map.get(DatabaseContract.Booking.COLUMN_NAME_BOOKING_ID));
            values.put(DatabaseContract.Booking.COLUMN_NAME_USER_REFERENCE_ID, map.get(DatabaseContract.Booking.COLUMN_NAME_USER_REFERENCE_ID));
            values.put(DatabaseContract.Booking.COLUMN_NAME_START_DATE, map.get(DatabaseContract.Booking.COLUMN_NAME_START_DATE));
            values.put(DatabaseContract.Booking.COLUMN_NAME_END_DATE, map.get(DatabaseContract.Booking.COLUMN_NAME_END_DATE));
            values.put(DatabaseContract.Booking.COLUMN_NAME_LOCKER_ID, map.get(DatabaseContract.Booking.COLUMN_NAME_LOCKER_ID));
            values.put(DatabaseContract.Booking.COLUMN_NAME_PAYMENT_MODE, map.get(DatabaseContract.Booking.COLUMN_NAME_PAYMENT_MODE));
            values.put(DatabaseContract.Booking.COLUMN_NAME_PAYMENT_AMOUNT, map.get(DatabaseContract.Booking.COLUMN_NAME_PAYMENT_AMOUNT));


            db.insert(DatabaseContract.Booking.TABLE_NAME, null, values);

        } else if (tableName == DatabaseContract.Locker.TABLE_NAME) {

            values.put(DatabaseContract.Locker.COLUMN_NAME_LOCKER_ID, map.get(DatabaseContract.Locker.COLUMN_NAME_LOCKER_ID));
            values.put(DatabaseContract.Locker.COLUMN_NAME_AVAILABILITY, map.get(DatabaseContract.Locker.COLUMN_NAME_AVAILABILITY));

            db.insert(DatabaseContract.Booking.TABLE_NAME, null, values);


        }

    }


    @Override
    public String[] fetchAvailableLockers() {

        Cursor cursor = db.rawQuery("Select LOCKER_ID from Locker where AVAILABILITY = ?",
                new String[]{"1"});

        String[] lockerArray = new String[cursor.getCount()];

        if (cursor != null && cursor.moveToFirst()) {

            int i = 0;

            do {
                lockerArray[i] = cursor.getString(0);
                i++;
            }
            while (cursor.moveToNext());

        }

        return lockerArray;

    }

    @Override
    public Boolean checkIfMobileNumberAlreadyExists(String mobNum) {


        Cursor cursor = dbFetchQuery(DatabaseContract.User.TABLE_NAME,
                new String[]{DatabaseContract.User.COLUMN_NAME_MOBILE_NUMBER},
                DatabaseContract.User.COLUMN_NAME_MOBILE_NUMBER,
                new String[]{mobNum},
                null);

        if (cursor.getCount() == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public Boolean checkMobNumAndRefID(String mobNum, String refID) {

        Cursor cursor = db.rawQuery("Select * from User where MOBILE_NUMBER = ? and REF_ID = ?",
                new String[]{mobNum, refID});

        if (cursor.getCount() > 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }


    public String fetchReferenceID(String mobileNumber) {

        Cursor cursor = dbFetchQuery(DatabaseContract.User.TABLE_NAME,
                new String[]{DatabaseContract.User.COLUMN_NAME_REFERENCE_ID},
                DatabaseContract.User.COLUMN_NAME_MOBILE_NUMBER,
                new String[]{mobileNumber},
                null);

        String value = null;

        if (cursor != null && cursor.moveToFirst()) {
            value = cursor.getString(0);
        }
        return value;
    }

    private Cursor dbFetchQuery(String tableName, String[] desiredColumns, String selection, String[] selectionArgs, String sortOrder) {

        //Providing correct format for selection criteria
        selection = selection + " = ?";

        Cursor cursor = db.query(
                tableName,   // The table to query
                desiredColumns,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;


    }

    @Override
    public void update() {

    }
}
