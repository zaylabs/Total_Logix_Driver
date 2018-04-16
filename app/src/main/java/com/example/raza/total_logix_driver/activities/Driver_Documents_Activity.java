package com.example.raza.total_logix_driver.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_driver.DTO.driverDocuments;

import com.example.raza.total_logix_driver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Driver_Documents_Activity extends AppCompatActivity {
    private static final String TAG = "";
    private FirebaseAuth mAuth;
    String userID;
    private View view;
    private static final int RESULT_LOAD_IMAGE = 1;
    private Button mVerifyEmail;
    private ImageView mCNICFRONT;
    private ImageView mCNICBACK;
    private ImageView mDLFRONT;
    private ImageView mDLBACK;
    private ImageView mREG;

    private Uri mCNICFRONTuri;
    private Uri mCNICBACKuri;
    private Uri mDLFRONTuri;
    private Uri mDLBACKuri;
    private Uri mREGuri;

    private String dl_back;
    private String cnic_front;
    private String cnic_back;
    private String car_reg_doc;
    private Date docUploadDate;
    private String dl_front;

    private TextView mCNICFRONTtextviewStatus;
    private TextView mCNICBACKtextviewStatus;
    private TextView mDLFRONTtextviewStatus;
    private TextView mDLBACKtextviewStatus;
    private TextView mREGtextviewStatus;

    private StorageReference mImageRef;
    private FirebaseFirestore db;

    private StorageReference mStorageRef;

    private static final int CNICFRONTSAVE = 111;
    private static final int CNICBACKSAVE = 222;
    private static final int DLFRONTSAVE = 333;
    private static final int DLBACKSAVE = 444;
    private static final int REGSAVE = 555;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__documents_);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mImageRef = mStorageRef.child(userID);
        db = FirebaseFirestore.getInstance();


        mCNICFRONT = findViewById(R.id.IMG_CNIC_Front);
        mCNICBACK = findViewById(R.id.IMG_CNIC_Back);
        mDLFRONT = findViewById(R.id.IMG_DL_FRONT);
        mDLBACK = findViewById(R.id.IMG_DL_BACK);
        mREG = findViewById(R.id.IMG_REG);


        mCNICFRONTtextviewStatus = findViewById(R.id.TEXVIEWCNICFrontStatus);
        mCNICBACKtextviewStatus = findViewById(R.id.TEXVIEWCNICBackStatus);
        mDLFRONTtextviewStatus = findViewById(R.id.TEXVIEWDLFrontStatus);
        mDLBACKtextviewStatus = findViewById(R.id.TEXVIEWDLBackStatus);
        mREGtextviewStatus = findViewById(R.id.TEXVIEWREGStatus);

        mVerifyEmail = (Button)findViewById(R.id.verify_email);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        /*updateStatus();*/

    }


    public void sendEmailVerification(View view) {


        docUploadDate = Calendar.getInstance().getTime();

        driverDocuments driverDocuments= new driverDocuments(dl_front, dl_back, cnic_front,cnic_back, car_reg_doc, docUploadDate);
        db.collection("driverdocs").document(userID).set(driverDocuments);

        // Disable button
        // findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        //               findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(Driver_Documents_Activity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            startActivity(new Intent(Driver_Documents_Activity.this, LoginActivity.class));
                            finish();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Driver_Documents_Activity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    public void backToLogin(View view) {
        Intent intent = new Intent(Driver_Documents_Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        backToLogin(view);
    }

    public void cnicFront(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Driver_Documents_Activity.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CNICFRONTSAVE);
        }
    }


    private void saveCNICFRONT() {

        mCNICFRONT.setDrawingCacheEnabled(true);
        mCNICFRONT.buildDrawingCache();
        Bitmap bitmap = mCNICFRONT.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mImageRef.child("CNICFront").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mCNICFRONTuri = taskSnapshot.getDownloadUrl();
                cnic_front=mCNICFRONTuri.toString();

            }
        });
    }




    public void cnicBack(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Driver_Documents_Activity.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CNICBACKSAVE);
        }
    }


    private void saveCNICBACK() {

        mCNICBACK.setDrawingCacheEnabled(true);
        mCNICBACK.buildDrawingCache();
        Bitmap bitmap = mCNICBACK.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mImageRef.child("CNICBACK").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mCNICBACKuri = taskSnapshot.getDownloadUrl();
                cnic_back = mCNICBACK.toString();

            }
        });
    }


    public void dlFront(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Driver_Documents_Activity.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, DLFRONTSAVE);
        }
    }


    private void saveDLFRONT() {

        mDLFRONT.setDrawingCacheEnabled(true);
        mDLFRONT.buildDrawingCache();
        Bitmap bitmap = mDLFRONT.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mImageRef.child("DLFRONT").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mDLFRONTuri = taskSnapshot.getDownloadUrl();
                dl_front = mDLFRONTuri.toString();

            }
        });
    }

    public void dlBack(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Driver_Documents_Activity.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, DLBACKSAVE);
        }
    }


    private void saveDLBACK() {

        mDLBACK.setDrawingCacheEnabled(true);
        mDLBACK.buildDrawingCache();
        Bitmap bitmap = mDLBACK.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mImageRef.child("DLBACK").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mDLBACKuri = taskSnapshot.getDownloadUrl();
                dl_back=mDLBACKuri.toString();

            }
        });
    }


    public void regDoc(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Driver_Documents_Activity.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REGSAVE);
        }
    }


    private void saveREG() {

        mREG.setDrawingCacheEnabled(true);
        mREG.buildDrawingCache();
        Bitmap bitmap = mREG.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mImageRef.child("REG").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mREGuri = taskSnapshot.getDownloadUrl();
                car_reg_doc=mREGuri.toString();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CNICFRONTSAVE:
                if (resultCode == Driver_Documents_Activity.this.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mCNICFRONT.setImageBitmap(imageBitmap);
                    saveCNICFRONT();
                    //encodeBitmapAndSaveToFirebase(imageBitmap);
                }
                break;

            case CNICBACKSAVE:
                if (resultCode == Driver_Documents_Activity.this.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mCNICBACK.setImageBitmap(imageBitmap);
                    saveCNICBACK();
                    //encodeBitmapAndSaveToFirebase(imageBitmap);
                }
                break;

            case DLFRONTSAVE:
                if (resultCode == Driver_Documents_Activity.this.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mDLFRONT.setImageBitmap(imageBitmap);
                    saveDLFRONT();
                    //encodeBitmapAndSaveToFirebase(imageBitmap);
                }
                break;

            case DLBACKSAVE:
                if (resultCode == Driver_Documents_Activity.this.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mDLBACK.setImageBitmap(imageBitmap);
                    saveDLBACK();
                    //encodeBitmapAndSaveToFirebase(imageBitmap);
                }
                break;

            case REGSAVE:
                if (resultCode == Driver_Documents_Activity.this.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mREG.setImageBitmap(imageBitmap);
                    saveREG();
                    //encodeBitmapAndSaveToFirebase(imageBitmap);
                }
                break;
        }
    }


}

