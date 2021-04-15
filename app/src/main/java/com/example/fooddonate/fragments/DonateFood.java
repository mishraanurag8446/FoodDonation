package com.example.fooddonate.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.fooddonate.models.DonationData;
import com.example.fooddonate.R;
import com.example.fooddonate.models.UserData;
import com.example.fooddonate.operations.DbHandler;
import com.example.fooddonate.operations.MainRecycleViewAdaptor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class DonateFood extends Fragment {
    private Spinner states,foodcat;
    private EditText etvfoodName,etvmobile,etvaddBuilding,etvaddArea,etvaddPin,etvfoodPackets;
    private FirebaseFirestore firebaseFirestore;
    private Button btnSubmit;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String state,foodCategory,foodName,mobile,addBuilding,addArea,addPin,foodPacket;
    ProgressDialog progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_food,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        etvfoodName = view.findViewById(R.id.etvFoodName);
        etvmobile = view.findViewById(R.id.etvMobileNo);
        etvaddBuilding = view.findViewById(R.id.etvAdressOne);
        etvaddArea = view.findViewById(R.id.etvAddressTwo);
        etvaddPin = view.findViewById(R.id.etvPinCode);
        etvfoodPackets = view.findViewById(R.id.etvTotalPacks);
        states = view.findViewById(R.id.state);
        foodcat = view.findViewById(R.id.foodCategory);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit) ;

        progressBar= new ProgressDialog(getContext());
        progressBar.setCancelable(false); //you can cancel it by pressing back button
        progressBar.setMessage("Creating Donation...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.india_states));
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.foodCategory));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner

        states.setAdapter(aa);
        foodcat.setAdapter(adapter);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etvfoodName.getText().toString()))
                {
                    etvfoodName.setError("Food Name Required");
                    return;
                }
                if (TextUtils.isEmpty(etvmobile.getText().toString()))
                {
                    etvmobile.setError("Mobile Number Required");
                    return;
                }
                if (etvmobile.length()<10||etvmobile.length()>10)
                {
                    etvmobile.setError("Enter Valid Mobile no");
                }
                if (TextUtils.isEmpty(etvaddBuilding.getText().toString()))
                {
                    etvaddBuilding.setError("Address Required");
                    return;
                }
                if (TextUtils.isEmpty(etvaddArea.getText().toString()))
                {
                    etvaddArea.setError("Address Required");
                    return;
                }
                if (TextUtils.isEmpty(etvaddPin.getText().toString()))
                {
                    etvaddPin.setError("PinCode Required");
                    return;
                }
                if (TextUtils.isEmpty(etvfoodPackets.getText().toString()))
                {
                    etvfoodPackets.setError("Number of packets Required");
                    return;
                }
                progressBar.show();
                DonationData donateData = new DonationData();
                String userId = firebaseAuth.getCurrentUser().getUid();
                if (firebaseUser!=null)
                {
                    donateData.setEmailId(firebaseUser.getEmail());
                }
                state = states.getSelectedItem().toString();
                foodCategory= foodcat.getSelectedItem().toString();
                foodName = etvfoodName.getText().toString();
                mobile = etvmobile.getText().toString();
                addBuilding = etvaddBuilding.getText().toString();
                addArea = etvaddArea.getText().toString();
                addPin = etvaddPin.getText().toString();
                foodPacket = etvfoodPackets.getText().toString();
//                donateData.setEmailId(em.get(0));

                String currentTime = Calendar.getInstance().getTime().toString();
                DbHandler dbHandler= new DbHandler(getContext());
//                dbHandler.ReadData()
                Cursor cursor = dbHandler.ReadData(userId);
                cursor.moveToFirst();
                donateData.setName(cursor.getString(1));
                donateData.setAddOne(addBuilding);
                donateData.setAddTwo(addArea);
                donateData.setState(state);
                donateData.setPinCode(addPin);
                donateData.setFoodName(foodName);
                donateData.setFoodType(foodCategory);
                donateData.setPacksNo(foodPacket);
                donateData.setPhoneNo(mobile);

                DocumentReference documentReference1 = firebaseFirestore.collection("foodDonation").document(userId+currentTime);
                documentReference1.set(donateData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.dismiss();
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.alert_dialog_positive);
                        Button btnOkay = dialog.findViewById(R.id.btnOkay);
                        TextView title = dialog.findViewById(R.id.alertTitle);
                        TextView message = dialog.findViewById(R.id.alertMessage);
                        title.setText("Donation Created");
                        message.setText("Donation Created successfully");
                        dialog.show();
                        btnOkay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                            }
                        });


                    }
                });
            }
        });

        return view;
    }


}
