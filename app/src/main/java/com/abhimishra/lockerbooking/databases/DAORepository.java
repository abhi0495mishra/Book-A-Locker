package com.abhimishra.lockerbooking.databases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAORepository {

    Boolean checkIfMobileNumberAlreadyExists(String mobNum);
    void update();

    Boolean checkMobNumAndRefID(String mobNum, String refID);

    void insert(Map<String,String> map, String tableName);
}
