package com.softwareengineering.faceattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button upload, browse;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Uri imageUri;

    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView;

        img = (ImageView)findViewById(R.id.imageview_id);
        upload = (Button) findViewById(R.id.upload_id);
        browse = (Button) findViewById(R.id.gallery_id);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectimage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();
            }
        });


    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();



        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        img.setImageURI(null);
                        Toast.makeText(MainActivity.this, "Succesfully uploaded", Toast.LENGTH_SHORT ).show();

                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT ).show();

            }
        });
    }

    private void selectimage() {

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            img.setImageURI(imageUri);

        }
    }
}