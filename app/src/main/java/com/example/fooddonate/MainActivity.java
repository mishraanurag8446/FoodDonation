package com.example.fooddonate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fooddonate.activities.LoginMain;
import com.example.fooddonate.fragments.DonateFood;
import com.example.fooddonate.fragments.Home;
import com.example.fooddonate.fragments.ProfilePage;
import com.example.fooddonate.operations.DbHandler;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String uId;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = (NavigationView) findViewById(R.id.nav);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.menuHeader_Username);
        uId = firebaseAuth.getInstance().getCurrentUser().getUid();
        DbHandler dbHandler = new DbHandler(getApplicationContext());
        Cursor cursor = dbHandler.ReadData(uId);
        cursor.moveToFirst();
        navUsername.setText(cursor.getString(1));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        firebaseFirestore = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = firebaseFirestore.collection("users").document(uId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
////                navHeadertxtName.setText(value.getString("fName"));
//
//            }
//        });

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainContainer, new Home());
        fragmentTransaction.commit();
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if (firebaseUser==null)
//        {
//            startActivity(new Intent(this,LoginMain.class));
//            finish();
//        }else
//        {
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
//        }
//
//    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.home:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, new Home());
                fragmentTransaction.commit();
                toolbar.setTitle(R.string.home);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.profile:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, new ProfilePage());
                fragmentTransaction.commit();
                toolbar.setTitle(R.string.profile);
                Toast.makeText(this, "profile is set", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.donate:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, new DonateFood());
                fragmentTransaction.commit();
                toolbar.setTitle(R.string.donateFood);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.logout:
                Intent logout = new Intent(this, LoginMain.class);
                startActivity(logout);
                finish();
                break;
        }
        return false;
    }
}