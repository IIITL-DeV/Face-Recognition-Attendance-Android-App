package com.softwareengineering.faceattendance;

import java.util.HashMap;

public class Utils {

    private static HashMap<String, Integer> subject2AttendanceMap;

    public static HashMap<String, Integer> getSubject2AttendanceMap() {
        return subject2AttendanceMap;
    }

    public static void setDummyData() {
        subject2AttendanceMap = new HashMap<>();

        subject2AttendanceMap.put("math",13);
        subject2AttendanceMap.put("hindi",50);
        subject2AttendanceMap.put("english",33);
        subject2AttendanceMap.put("bio",33);
        subject2AttendanceMap.put("chemistry",23);



    }

    public static String[] getAllSubjectNames() {
//        String[] arr  = {"math","hindi","english"};

        return subject2AttendanceMap.keySet().toArray(new String[0]);


}
}
