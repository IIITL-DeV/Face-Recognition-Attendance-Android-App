package com.softwareengineering.faceattendance;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Utils {

    private static HashMap<String, Integer> subject2AttendanceMap;
    static FirebaseFirestore db;
    public static HashMap<String, Integer> getSubject2AttendanceMap() {
        return subject2AttendanceMap;
    }

    public static void setDummyData(String rollnumber) {

        subject2AttendanceMap = new HashMap<>();

        subject2AttendanceMap.put("english",33);
        subject2AttendanceMap.put("bio",33);
        subject2AttendanceMap.put("chemistry",23);



    }

    public static String[] getAllSubjectNames() {
//        String[] arr  = {"math","hindi","english"};

        return subject2AttendanceMap.keySet().toArray(new String[0]);


}
}
