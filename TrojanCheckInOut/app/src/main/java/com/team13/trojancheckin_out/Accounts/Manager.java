package com.team13.trojancheckin_out.Accounts;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.Layouts.BuildingAdapter;
import com.team13.trojancheckin_out.Layouts.ManagerLanding;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Manager class is an extension of the User class. The primary difference is that a Manager
 * has access to certain facilitation mechanisms that would be absent from a regular user.
 */
public class Manager extends User {

    BuildingManipulator buildingManipulator;
    AccountManipulator accountManipulator;
    public Manager(BuildingManipulator buildingManipulator, AccountManipulator accountManipulator) {
        this.buildingManipulator = buildingManipulator;
        this.accountManipulator = accountManipulator;
    }

    /**
     * @param file
     * @return true if the CSV file has been successfully imported.
     */
    public Boolean importCSV(File file) {
        buildingManipulator.processCSV(file);
        return true;
    }

    /**
     * @param building
     * @param capacity
     * @return true if the building capacity has been successfully updated.
     */
    public Boolean updateBuildingCapacity(Building building, int capacity) {
        buildingManipulator.referenceBuildings.child(building.getAbbreviation()).child("capacity").setValue(capacity);
        return true;
    }

    /**
     * @param building
     * @return a list of the current users in a selected building.
     */
    public List<User> getPeopleInBuilding(Building building) {
        //return buildingManipulator.getCurrentBuildings().get(building).getCurrentStudents();
        return new ArrayList<User>();
    }

    /**
     * @param building
     * @return true if the manager was successfully able to retrieve the appropriate building QRCode
     * and show it to the user in question.
     */
    public Boolean showQRCode(Building building){ return true; }

    /**
     * @param time
     * @param building
     * @param id
     * @param major
     * @return the searched student.
     */
    public List<User> searchStudents(int startTime, int endTime, Building building, String id, String major) {
        // IF WE ARE NOT SEARCHING BY TIME, ENTER "-1" into the startTime parameter.

<<<<<<< HEAD
        List<User> list = new ArrayList<>();

        if (id != null) {
            accountManipulator.getAllAccounts(new MyUserCallback() {
                @Override
                public void onCallback(Map<String, User> map) {
                    for (Map.Entry<String, User> user : map.entrySet()) {
                        if (id.equals(user.getValue().getId())) {
                            list.add(user.getValue());
                        }
                    }
                }
            });
            return list;
        } else if (building != null) {
            if (major != null && startTime != -1) {
                for (User user : building.getCurrentStudents()) {
                    TimeStamps ts = user.getHistory().get(building);
                    if (user.getMajor().equals(major) && ts.checkInTime >= startTime && ts.checkOutTime <= endTime) {
                        list.add(user);
                    }
                }
                return list;
            } else if (major != null && startTime == -1) {
                for (User user : building.getCurrentStudents()) {
                    if (user.getMajor().equals(major)){
                        list.add(user);
                    }
                }
                return list;
            } else {
                return building.getCurrentStudents();
            }
        } else if (major != null) {
            if (startTime != -1) {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> user : map.entrySet()) {
                            TimeStamps ts = user.getValue().getHistory().get(building);
                            if (user.getValue().getMajor().equals(major) && ts.checkInTime >= startTime && ts.checkOutTime <= endTime) {
                                list.add(user.getValue());
                            }
                        }
                    }
                });
            } else {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> user : map.entrySet()) {
                            if (user.getValue().getMajor().equals(major)) {
                                list.add(user.getValue());
                            }
                        }
                    }
                });
            }
            return list;
        }
=======
          List<User> list = new ArrayList<>();
//        if (id != null) {
//            list.add(accountManipulator.getStudentAccounts().get(id));
//            return list;
//        } else if (building != null){
//            if (major != null) {
//                for (User user : building.getCurrentStudents()) {
//                    if (user.getMajor().equals(major)){
//                        list.add(user);
//                    }
//                }
//                return list;
//            } else {
//                return building.getCurrentStudents();
//            }
//        } else if (major != null) {
//            for (User user : accountManipulator.getStudentAccounts().values()) {
//                if (user.getMajor().equals(major)) {
//                    list.add(user);
//                }
//            }
//            return list;
//        }
>>>>>>> bb81b69a3de206846fc1175c1afda66dc01acbd1

        return list;
    }
}
