package com.softwareengineering.faceattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
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
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadImage extends AppCompatActivity {

    ImageView img;
    Button upload, takephoto;
    EditText roll, name;
    Uri filepath;

    Bitmap bitmap;
    FirebaseFirestore db;
    FirebaseFirestore db2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        //db2 = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        img = (ImageView)findViewById(R.id.imageview_id);
        upload = (Button) findViewById(R.id.upload_id);
        takephoto = (Button) findViewById(R.id.gallery_id);
        roll = (EditText) findViewById(R.id.rollno_id);
        name = (EditText) findViewById(R.id.name_id);

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UploadImage.this)
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

                DateTimeFormatter dtf = null;
                String date = "didnotwork";

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("uuuuMMdd");
                   DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HHmm");
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    date = dtf.format(localDate) + dtfTime.format(localTime);
                }


                Map<String, Object> blank = new HashMap<>();
                blank.put("name", name.getText().toString());
                blank.put("lastupdated", date);


                db.collection("students")
                        .document(roll.getText().toString())
                        .set(blank);

                Map<String,Object> blank2 = new HashMap<>();
                db.collection(roll.getText().toString())
                        .document("validproof")
                        .set(blank2);

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


                        Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                       // Bundle mBundle = new Bundle();
                      //  mBundle.putString("rollnum", roll.getText().toString());
                       // Intent intent = new Intent(UploadImage.this, DashboardActivity.class);
                       // startActivity(intent);
                        //finish();
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