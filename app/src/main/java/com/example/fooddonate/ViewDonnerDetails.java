package com.example.fooddonate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDonnerDetails extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doner_details);
//        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
//        textView.setText(intent.getExtras().getString("title"));
    }
}