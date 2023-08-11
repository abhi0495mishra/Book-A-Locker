package com.abhimishra.lockerbooking.databases;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "USER";
        public static final String COLUMN_NAME_REFERENCE_ID = "REF_ID";  //Primary Key
        public static final String COLUMN_NAME_MOBILE_NUMBER = "MOBILE_NUMBER";
        public static final String COLUMN_NAME_PAYMENT_DETAILS = "PAYMENT_DETAILS";
        public static final String COLUMN_NAME_LIST_OF_BOOKINGS = "LIST_OF_BOOKING";
    }

    public static class Booking implements BaseColumns {
        public static final String TABLE_NAME = "BOOKING";
        public static final String COLUMN_NAME_BOOKING_ID = "BOOKING_ID"; // Primary Key
        public static final String COLUMN_NAME_USER_REFERENCE_ID = "USER_REF_ID";
        public static final String COLUMN_NAME_START_DATE = "START_DATE";
        public static final String COLUMN_NAME_END_DATE = "END_DATE";
        public static final String COLUMN_NAME_LOCKER_ID = "LOCKER_ID";
        public static final String COLUMN_NAME_PAYMENT_MODE = "PAYMENT_MODE";
        public static final String COLUMN_NAME_PAYMENT_AMOUNT = "PAYMENT_AMOUNT";
    }

    public static class Locker implements BaseColumns {
        public static final String TABLE_NAME = "LOCKER";
        public static final String COLUMN_NAME_LOCKER_ID = "LOCKER_ID"; // Primary Key
        public static final String COLUMN_NAME_AVAILABILITY = "AVAILABILITY";
    }
}
