package com.abhimishra.lockerbooking.databases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAORepository {

    String[] fetchAvailableLockers();

    Boolean checkIfMobileNumberAlreadyExists(String mobNum);

    void updateLockerAvailabilityFalse(String locker);
    void updateLockerAvailabilityTrue(String locker);

    void cancelBooking(String bookingID);

    Boolean checkMobNumAndRefID(String mobNum, String refID);

    void insert(Map<String, String> map, String tableName);
}
