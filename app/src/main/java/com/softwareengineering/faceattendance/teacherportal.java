package com.softwareengineering.faceattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class teacherportal extends AppCompatActivity {

    EditText subjectname, batchname, batchyear, startday, starttime, endtime;
    Button uploadButton;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherportal);

        db = FirebaseFirestore.getInstance();
        subjectname = (EditText) findViewById(R.id.subjectNameId);
        batchname = (EditText) findViewById(R.id.batchId);
        batchyear = (EditText) findViewById(R.id.batchYearId);
        startday = (EditText) findViewById(R.id.startDayId);
        starttime = (EditText) findViewById(R.id.startTimeId);
        endtime = (EditText) findViewById(R.id.endTimeId);
        uploadButton = (Button) findViewById(R.id.uploadButtonID);


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> classdetails = new HashMap<>();
                classdetails.put("subject", subjectname.getText().toString());
                classdetails.put("batch", batchname.getText().toString());
                classdetails.put("batchyear", batchyear.getText().toString());
                classdetails.put("startday", startday.getText().toString());
                classdetails.put("starttime", starttime.getText().toString());
                classdetails.put("endtime", endtime.getText().toString());

                db.collection("classes")
                        .document(subjectname.getText().toString() + batchyear.getText().toString())
                        .set(classdetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(teacherportal.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(teacherportal.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        /*
        Map<String, String> config1 = new HashMap<>();
        config1.put("lololol1", "url1");
        config1.put("lololol2", "url1");
        config1.put("lololol3", "url1");

        Map<String, String> config2 = new HashMap<>();
        config2.put("lololol1", "url1");
        config2.put("lololol2", "url1");
        config2.put("lololol3", "url1");

        Map<String, Map<String, String>> map = new HashMap<>();
        map.put("config1", config1);
        map.put("config2", config2);

        Data data = new Data(map);

        Gson gson = new Gson();
        String jsonString = gson.toJson(data);

        File rootFolder = getApplicationContext().getExternalFilesDir(null);
        File jsonFile = new File(rootFolder, "file.json");

        try {
            FileWriter writer = new FileWriter(jsonFile);
            writer.write(jsonString);
            writer.close();
            Toast.makeText(getApplicationContext(), "json created!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

         */

    }
}