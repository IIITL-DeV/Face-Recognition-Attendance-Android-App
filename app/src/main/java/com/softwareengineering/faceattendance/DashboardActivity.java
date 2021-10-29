package com.softwareengineering.faceattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ClassRecyclerViewAdapter recyclerViewAdapter = new ClassRecyclerViewAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.dashboardRecyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);



    }
}