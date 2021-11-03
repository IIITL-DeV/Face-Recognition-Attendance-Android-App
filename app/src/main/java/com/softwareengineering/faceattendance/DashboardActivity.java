package com.softwareengineering.faceattendance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public String rollnumber;
    ArrayList<user> userArrayList;
    ClassRecyclerViewAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    ImageView noDataFoundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //ClassRecyclerViewAdapter recyclerViewAdapter = new ClassRecyclerViewAdapter(this,rollnumber);
        noDataFoundImage = (ImageView) findViewById(R.id.nodatafoundImageId);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data..");

        progressDialog.show();

        rollnumber = getIntent().getExtras().getString("rollnum");
        recyclerView = (RecyclerView) findViewById(R.id.dashboardRecyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<user>();
        myAdapter = new ClassRecyclerViewAdapter(DashboardActivity.this, userArrayList);
        recyclerView.setAdapter(myAdapter);
       // recyclerView.setAdapter(recyclerViewAdapter);

        EventChangedListener();


    }

    private void EventChangedListener() {

        db.collection(rollnumber).orderBy("subject", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error!=null)
                        {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        if(value.getDocumentChanges().size()==0)
                        {
                            noDataFoundImage.setVisibility(View.VISIBLE);
                        }
                        for (DocumentChange dc : value.getDocumentChanges())
                        {
                            if(dc.getType()==DocumentChange.Type.ADDED)
                            {
                                userArrayList.add(dc.getDocument().toObject(user.class));
                            }

                            myAdapter.notifyDataSetChanged();
                        }
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();


                    }
                });
    }
}