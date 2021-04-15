package com.example.fooddonate.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fooddonate.R;
import com.example.fooddonate.operations.DbHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilePage extends Fragment{
    TextView tvName;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView tvMobileNo,tvEmail,tvAddress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        tvName = view.findViewById(R.id.Userna);
        tvMobileNo = view.findViewById(R.id.tvPMobileNo);
        tvEmail = view.findViewById(R.id.tvPEmail);
        tvAddress = view.findViewById(R.id.tvPAddress);

        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        DbHandler dbHandler= new DbHandler(getContext());
        Cursor cursor = dbHandler.ReadData(userId);
        cursor.moveToFirst();
        tvName.setText(cursor.getString(1));
        tvMobileNo.setText(cursor.getString(2));
        tvEmail.setText(cursor.getString(3));
        tvAddress.setText(cursor.getString(4));

        return view;
    }
}