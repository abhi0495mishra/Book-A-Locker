package com.abhimishra.lockerbooking.databases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAORepository {

    String[] fetchAvailableLockers();

    Boolean checkIfMobileNumberAlreadyExists(String mobNum);

    void updateLockerAvailability(String locker);

    Boolean checkMobNumAndRefID(String mobNum, String refID);

    void insert(Map<String, String> map, String tableName);
}
