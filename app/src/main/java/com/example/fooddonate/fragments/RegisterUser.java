package com.example.fooddonate.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fooddonate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    EditText txtname,txtphone,txtemail,password,confirmPass;
    Button btnRegister;
    String userId;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProgressDialog progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        txtemail = view.findViewById(R.id.etvEmail);
        txtname = view.findViewById(R.id.etvName);
        txtphone = view.findViewById(R.id.etvRphone);
        password = view.findViewById(R.id.etvRPass);
        confirmPass = view.findViewById(R.id.etvConfirmPass);
        btnRegister = view.findViewById(R.id.btnRegister);
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar= new ProgressDialog(getContext());

//        tabLayout = (TabLayout) view.findViewById(R.id.myTab);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPager = (ViewPager) container.findViewById(R.id.myPager);

//        BasePagerAdapter bpa = (BasePagerAdapter) mViewPager.getAdapter();
//        MySiblingFragment f = (MySiblingFragment) bpa.getItem(1);
//        viewPager = (ViewPager) view.findViewById(R.id.myPager);

        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Registering user...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegister();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    void getRegister()
    {
        String fullName,email,mobile;
        fullName = txtname.getText().toString();
        email = txtemail.getText().toString();
        mobile = txtphone.getText().toString();


        if (TextUtils.isEmpty(txtname.getText().toString()))
        {
            txtname.setError("Name Require");
            return;
        }
        if (TextUtils.isEmpty(email))
        {
            txtemail.setError("Email Require");
            return;
        }
        if (TextUtils.isEmpty(txtphone.getText().toString()))
        {
            txtphone.setError("Mobile no Require");
            return;
        }

        if (TextUtils.isEmpty(password.getText().toString())||password.length()<6)
        {
            password.setError("Password Require and length must be at least 6");
            return;
        }
        if (confirmPass.getText().equals(password.getText().toString())){
            confirmPass.setError("Password doesn't match");
            return;
        }
        progressBar.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password.getText().toString())
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            userId = firebaseAuth.getCurrentUser().getUid();
                            Log.d("TAG", "createUserWithEmail:success");
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("userId",userId);
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone_no",mobile);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                                    Dialog dialog = new Dialog(getContext());
                                    dialog.setContentView(R.layout.alert_dialog_positive);
                                    Button btnOkay = dialog.findViewById(R.id.btnOkay);
                                    TextView title = dialog.findViewById(R.id.alertTitle);
                                    TextView message = dialog.findViewById(R.id.alertMessage);
                                    title.setText("Registrations");
                                    message.setText("User Registered successfully");
                                    dialog.show();
                                    btnOkay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            progressBar.dismiss();
                                            dialog.dismiss();

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Dialog dialog = new Dialog(getContext());
                                    dialog.setContentView(R.layout.alert_dialog_positive);
                                    Button btnOkay = dialog.findViewById(R.id.btnOkay);
                                    btnOkay.setText("try again...");
                                    TextView title = dialog.findViewById(R.id.alertTitle);
                                    TextView message = dialog.findViewById(R.id.alertMessage);
                                    title.setText("Registrations");
                                    message.setText("User Registration failed");
                                    dialog.show();
                                    btnOkay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            progressBar.dismiss();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

    }
}
