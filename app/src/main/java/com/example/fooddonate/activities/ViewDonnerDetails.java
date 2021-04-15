package com.example.fooddonate.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddonate.R;
import com.example.fooddonate.models.DonationData;
import com.example.fooddonate.models.UserData;
import com.example.fooddonate.operations.DbHandler;
import com.example.fooddonate.operations.MainRecycleViewAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ViewDonnerDetails extends AppCompatActivity {
    TextView name,tvToolbarTitle,tvMobileNo,tvEmail,tvPacks;
    ImageView profilePic,btnBack;
    String dname;
    int i = 0;
    ArrayList<String> userId,emailId,mobileno,Name;
    FirebaseFirestore firebaseFirestore;
    Map<String,String> Donnerdta;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doner_details);
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = new ArrayList<String>();
        mobileno = new ArrayList<String>();
        emailId = new ArrayList<String>();
        Name = new ArrayList<String>();


        progressBar= new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Creating Donation...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();


        Donnerdta =new HashMap<String,String>();
        name = findViewById(R.id.txtName);
        tvMobileNo = findViewById(R.id.tvDPMobileNo);
        tvEmail = findViewById(R.id.tvDPEmail);
        tvPacks = findViewById(R.id.tvDPDonationMade);
        profilePic = findViewById(R.id.imgProPic);
        btnBack = findViewById(R.id.btnToolbarBack);
        tvToolbarTitle = findViewById(R.id.tvtoolbarTitle);
        tvToolbarTitle.setText("DonnerDetails");
        Intent intent = getIntent();
        dname = intent.getStringExtra("Name");
        name.setText(dname);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firebaseFirestore.collection("users").whereEqualTo("fName",dname).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        UserData userData = document.toObject(UserData.class);
                        Name.add(userData.getName());
                        emailId.add(userData.getEmail());
                        userId.add(userData.getUserId());

                    }
                    Log.d("d", emailId.get(0) + "-------------------------");

                }
                tvEmail.setText(emailId.get(0));
                progressBar.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
//                    .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("info" ,"retrieving data fail");
//            }
//        });
        firebaseFirestore.collection("foodDonation").whereEqualTo("name",dname).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty())
                    {
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                            DonationData donationData = document.toObject(DonationData.class);
                            mobileno.add(donationData.getPhoneNo());
                            i++;
                        }
                        tvPacks.setText(""+i);
                        tvMobileNo.setText(mobileno.get(0));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("info" ,"retrieving data fail");
                }
            });

    }
}