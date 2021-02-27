package com.example.fooddonate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginMain extends AppCompatActivity {

    EditText userId,userPass;
    Button btnLogin;
    TextView tvForgetPass,tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        userId = (EditText) findViewById(R.id.etvUser);
        userPass = (EditText) findViewById(R.id.etvPass);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvForgetPass = (TextView)findViewById(R.id.btnForgetPass);
        tvSignUp = (TextView) findViewById(R.id.btnSignUp);

    }

    public void doLogin(View view) {
        Log.i("info","doLogin");
        if (userId.getText().toString().equals("abc")&&userPass.getText().toString().equals("pass"))
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else {
            Log.i("info","userId ans Password doesn't match");
        }
    }

    public void doSignUp(View view) {
        Log.i("info","DoSignUp");
    }

    public void doForgetPass(View view) {
        Log.i("info","Do forget pass");
    }

}