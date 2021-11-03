package com.softwareengineering.faceattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
    EditText roll, name;
    Uri filepath;

    Bitmap bitmap;
    Button login, signup;
    Dialog dialog, dialog2;
    Integer mode = 0;
    Integer sign_log = 0;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        mode == 0 -> blank state
        sign_log ==0 -> blank state

        mode == 1 -> Student
        mode == 2 -> Teacher

        sign_log ==1 -> Signin
        sign_log ==2 -> Login
         */

        db = FirebaseFirestore.getInstance();
        login = (Button) findViewById(R.id.login_Id);
        signup = (Button) findViewById(R.id.sign_up_id);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        final EditText rollnum = (EditText) dialog.findViewById(R.id.login_roll_id);
        final EditText name = (EditText) dialog.findViewById(R.id.login_name_id);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_buttonid);
        Button continuebutton = (Button) dialog.findViewById(R.id.continuebuttonid);

        dialog2 = new Dialog(MainActivity.this);
        dialog2.setContentView(R.layout.custom_dialogue_2);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(true);
        dialog2.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button studentButton = (Button) dialog2.findViewById(R.id.studentButtonId);
        Button teacherButton = (Button) dialog2.findViewById(R.id.teacherButtonId);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              db.collection(rollnum.getText().toString()).document("validproof")
                      .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                  @Override
                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                      if(documentSnapshot.exists())
                      {
                          Bundle mBundle = new Bundle();
                          mBundle.putString("rollnum", rollnum.getText().toString());
                          Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                          intent.putExtras(mBundle);
                          startActivity(intent);
                          finish();
                      }
                      else
                      {
                          Toast.makeText(MainActivity.this, "No data found",Toast.LENGTH_SHORT ).show();
                      }

                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {

                      Toast.makeText(MainActivity.this, "Failed",Toast.LENGTH_SHORT ).show();

                  }
              });
                /*
                db.collection(rollnum.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(), "Signed in sucessfully ", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "No data exists", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                 */


            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                rollnum.getText().clear();
                name.getText().clear();
            }
        });

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sign_log==1)
                {
                    Intent intent = new Intent(MainActivity.this, UploadImage.class);
                    startActivity(intent);
                   // finish();
                }

                else
                {
                    dialog.show();
                }


            }
        });

        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sign_log==1)
                {
                    Intent intent = new Intent(MainActivity.this, teacherportal.class);
                    startActivity(intent);
                    //finish();
                }

                else
                {
                    Intent intent = new Intent(MainActivity.this, teacherportal.class);
                    startActivity(intent);
                    //finish();
                    //dialog.show();
                }

            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sign_log = 1;
                dialog2.show();

                /*


                 */
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sign_log = 2;
                dialog2.show();

            }
        });



    }
}