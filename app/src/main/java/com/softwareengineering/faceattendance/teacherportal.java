package com.softwareengineering.faceattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherportal);

        subjectname = (EditText) findViewById(R.id.subjectNameId);
        batchname = (EditText) findViewById(R.id.batchId);
        batchyear = (EditText) findViewById(R.id.batchYearId);
        startday = (EditText) findViewById(R.id.startDayId);
        starttime = (EditText) findViewById(R.id.startTimeId);
        endtime = (EditText) findViewById(R.id.endTimeId);


        Map<String, String> config1 = new HashMap<>();
        config1.put("component1", "url1");
        config1.put("component2", "url1");
        config1.put("component3", "url1");

        Map<String, String> config2 = new HashMap<>();
        config2.put("component1", "url1");
        config2.put("component2", "url1");
        config2.put("component3", "url1");

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

    }
}

class Data {

    private Map<String, Map<String, String>> map;

    public Data() {
    }

    public Data(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    public Map<String, Map<String, String>> getMap() {
        return map;
    }

    public void setMap(Map<String, Map<String, String>> map) {
        this.map = map;
    }
}