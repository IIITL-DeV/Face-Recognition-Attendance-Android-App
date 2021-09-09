package com.softwareengineering.faceattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button upload, takephoto;

    Uri filepath;

    Bitmap bitmap;
    EditText roll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView;

        img = (ImageView)findViewById(R.id.imageview_id);
        upload = (Button) findViewById(R.id.upload_id);
        takephoto = (Button) findViewById(R.id.gallery_id);
        roll = (EditText) findViewById(R.id.rollno_id);


        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(MainActivity.this)
                      .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                      .withListener(new PermissionListener() {
                          @Override
                          public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                              Intent intent = new Intent(Intent.ACTION_PICK);
                              intent.setType("image/*");
                              startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                          }

                          @Override
                          public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                          }

                          @Override
                          public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                              permissionToken.continuePermissionRequest();
                          }
                      }).check();

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   uploadtofirebase();

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
           filepath = data.getData();

           try {
               InputStream inputStream = getContentResolver().openInputStream(filepath);
               bitmap = BitmapFactory.decodeStream(inputStream);

               img.setImageBitmap(bitmap);
           }

           catch (Exception ex){

           }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private void uploadtofirebase()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("faces/" + roll.getText().toString());
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                        Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                    }
                })

                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                    {
                      float percent = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                      dialog.setMessage("Uploaded :" +(int)percent+ "%");
                    }
                });

    }
}