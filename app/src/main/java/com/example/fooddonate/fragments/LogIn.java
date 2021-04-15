package com.example.fooddonate.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooddonate.MainActivity;
import com.example.fooddonate.R;
import com.example.fooddonate.operations.DbHandler;
import com.example.fooddonate.operations.SendMail;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class LogIn extends Fragment {

    EditText userId,userPass;
    Button btnLogin;
    ProgressDialog progressBar;
    TextView tvForgetPass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DbHandler dbHandler;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
//        init(view);
        context = container.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
//        dbHandler.onCreate();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = (EditText) view.findViewById(R.id.etvUser);
        userPass = (EditText) view.findViewById(R.id.etvPass);
        btnLogin =(Button) view.findViewById(R.id.btnLogin);
        tvForgetPass = (TextView) view.findViewById(R.id.btnForgetPass);
        progressBar= new ProgressDialog(getContext());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Verifying User");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Forget password clicked", Toast.LENGTH_SHORT).show();
                Log.d("info","forget Clicked----------------------------------------------------------------------------");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_forget_pass);
                TextView etvEmail = dialog.findViewById(R.id.etvFEmail);
                Button btnRequest = dialog.findViewById(R.id.btnFRequest);
                Button btnCancel = dialog.findViewById(R.id.btnFCancel);
                dialog.show();
                btnRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(etvEmail.getText()))
                        {
                            etvEmail.setError("Email Required");
                            return;
                        }
                        auth.sendPasswordResetEmail(etvEmail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            // do something when mail was sent successfully.
                                            Toast.makeText(getContext(), "Password reset link has been send successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Password reset Failed", Toast.LENGTH_SHORT).show();
                                            // ...
                                        }
                                    }
                                });
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(view);
            }
        });

        return view;
    }

//    private void init(View view) {
//        btnLogin =(Button) view.findViewById(R.id.btnLogin);
//    }

//    void setHome()
//    {
//        Intent intent = new Intent(context, MainActivity.class);
//        startActivity(intent);
//    }



    public void doLogin(View view) {

        String username,userPas;
        username = userId.getText().toString();
        userPas = userPass.getText().toString();
        Log.i("info",TextUtils.isEmpty(username)+""+TextUtils.isEmpty(userPas)+"");
        if (TextUtils.isEmpty(username))
        {
            userId.setError("Username can't be empty enter email");
            return;
        }
        if (TextUtils.isEmpty(userPas)||userPass.length()<6)
        {
            userPass.setError("Password Required and length should be at least 6");
            return;
        }
//
        progressBar.show();
        Log.i("info","doLogin");
        firebaseAuth.signInWithEmailAndPassword(username, userPas)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String uId = firebaseAuth.getCurrentUser().getUid();
                            firebaseFirestore = FirebaseFirestore.getInstance();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(uId);
                            documentReference.addSnapshotListener((Activity) getContext(), new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    dbHandler = new DbHandler(getContext());
                                    dbHandler.WriteData(value.getString("userId"),value.getString("fName"),value.getString("email"),value.getString("phone_no"),"null");
                                    Log.d("TAG","data inserted in database");
                                    Log.d("TAG", "signInWithEmail:success");
                                    Intent intent = new Intent(context,MainActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    progressBar.dismiss();
                                }
                            });
                            ((Activity) context).finish();

                        } else {

                            Dialog dialog = new Dialog(getContext());
                            dialog.setContentView(R.layout.alert_dialog_positive);
                            Button btnOkay = dialog.findViewById(R.id.btnOkay);
                            TextView title = dialog.findViewById(R.id.alertTitle);
                            TextView message = dialog.findViewById(R.id.alertMessage);
                            title.setText("Login failed");
                            message.setText("Username and Password does't match");
                            btnOkay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    progressBar.dismiss();
                                    dialog.dismiss();

                                }
                            });
                            dialog.show();
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
