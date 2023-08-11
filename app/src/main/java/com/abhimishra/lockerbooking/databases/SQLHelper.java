package com.abhimishra.lockerbooking.databases;

public class SQLHelper {

    protected static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + DatabaseContract.User.TABLE_NAME + " (" +
                    DatabaseContract.User.COLUMN_NAME_REFERENCE_ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.User.COLUMN_NAME_MOBILE_NUMBER + " TEXT UNIQUE," +
                    DatabaseContract.User.COLUMN_NAME_LIST_OF_BOOKINGS + " TEXT," +
                    DatabaseContract.User.COLUMN_NAME_PAYMENT_DETAILS + " TEXT);";


    protected static final String SQL_CREATE_BOOKING_ENTRIES =
            "CREATE TABLE " + DatabaseContract.Booking.TABLE_NAME + " (" +
                    DatabaseContract.Booking.COLUMN_NAME_BOOKING_ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Booking.COLUMN_NAME_USER_REFERENCE_ID + " TEXT," +
                    DatabaseContract.Booking.COLUMN_NAME_START_DATE + " TEXT," +
                    DatabaseContract.Booking.COLUMN_NAME_END_DATE + " TEXT," +
                    DatabaseContract.Booking.COLUMN_NAME_LOCKER_ID + " TEXT," +
                    DatabaseContract.Booking.COLUMN_NAME_PAYMENT_AMOUNT + " TEXT," +
                    DatabaseContract.Booking.COLUMN_NAME_PAYMENT_MODE + " TEXT);";

    protected static final String SQL_CREATE_LOCKER_ENTRIES =
            "CREATE TABLE " + DatabaseContract.Locker.TABLE_NAME + " (" +
                    DatabaseContract.Locker.COLUMN_NAME_LOCKER_ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Locker.COLUMN_NAME_AVAILABILITY + " INTEGER);";


    protected static final String SQL_DELETE_USER_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.User.TABLE_NAME + ";";

    protected static final String SQL_DELETE_BOOKING_ENTRIES =
                    "DROP TABLE IF EXISTS " + DatabaseContract.Booking.TABLE_NAME + ";";


    protected static final String SQL_DELETE_LOCKER_ENTRIES =
                    "DROP TABLE IF EXISTS " + DatabaseContract.Locker.TABLE_NAME + ";";
}
