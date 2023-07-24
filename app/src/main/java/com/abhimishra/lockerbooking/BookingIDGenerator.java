package com.abhimishra.lockerbooking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class BookingIDGenerator {

    // Function to generate a random number within a specific range
    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    // Function to generate a unique booking ID
    public static String generateBookingID() {
        // Get current timestamp
        long timestamp = System.currentTimeMillis();

        // Generate a random 3-digit number
        int randomThreeDigitNumber = generateRandomNumber(100, 1000);

        // Combine timestamp and random number to create the booking ID
        String bookingID = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date(timestamp)) + randomThreeDigitNumber;

        return bookingID;
    }
}
