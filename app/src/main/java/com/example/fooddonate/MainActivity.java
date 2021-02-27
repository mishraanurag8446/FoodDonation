package com.example.fooddonate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FrameLayout mainContainer;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout =  findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainContainer,new Home());
        fragmentTransaction.commit();
//
//        mainRecycleView.setAdapter(mainRecycleViewAdaptor);
//        mainContainer = (FrameLayout) findViewById(R.id.mainContainer);
//        mainRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }


//    @Override
//    public void onRecyclerViewItemClicked(int position) {

//    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId())
//        {
//
//        }
//        return false;
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
//    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


//        @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }


//
//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.home:
//                TextView textView = new TextView(this);
//                textView.setText("this is new textview");
//                mainContainer.addView(textView);
//                {
//                    setmCurrentActivity("home");
//                    Intent intent = new Intent(this,MainActivity.class);
//                    startActivity(inte
                break;
            case R.id.profile:

//                if (!getmCurrentActivity().equals("profile"))
//                {
//                    setmCurrentActivity("profile");
//                    Intent intent1 = new Intent(this,ProfilePage.class);
//                    startActivity(intent1);
//                }
                break;
        }
        return false;
    }
}