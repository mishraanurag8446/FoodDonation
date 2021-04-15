package com.example.fooddonate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddonate.operations.MainRecycleViewAdaptor;
import com.example.fooddonate.R;
import com.example.fooddonate.models.DonationData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home extends Fragment{
    
    RecyclerView mainRecycleView;
    String des;
    ArrayList<String> name,foodname,address,foodType,noOfPacks,mobileNo,emailId;
    MainRecycleViewAdaptor mainRecycleViewAdaptor;
    FirebaseFirestore firebaseFirestore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        name = new ArrayList<String>();
        foodname = new ArrayList<String>();
        address = new ArrayList<String>();
        foodType = new ArrayList<String>();
        noOfPacks = new ArrayList<String>();
        mobileNo = new ArrayList<String>();
        emailId = new ArrayList<String>();
        mainRecycleView = (RecyclerView) view.findViewById(R.id.rvMainHolder);
        mainRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();
//        mainRecycleView.setAdapter(new MainRecycleViewAdaptor(getContext(),name,foodname,address,foodType,noOfPacks,mobileNo));

        return view;
    }

    void getData()
    {
        firebaseFirestore.collection("foodDonation").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        DonationData donationData = document.toObject(DonationData.class);
                        name.add(donationData.getName());
                        foodname.add(donationData.getFoodName());
                        des = donationData.getAddOne()+",\n"+donationData.getAddTwo()+",\n"+donationData.getState()+",\n"+donationData.getPinCode();
                        address.add(des);
                        foodType.add(donationData.getFoodType());
                        noOfPacks.add(donationData.getPacksNo());
                        mobileNo.add(donationData.getPhoneNo());
                        emailId.add(donationData.getEmailId());
                    }
                }
                mainRecycleView.setAdapter(new MainRecycleViewAdaptor(getContext(),name,foodname,address,foodType,noOfPacks,mobileNo,emailId));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("info" ,"retrieving data fail");

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
