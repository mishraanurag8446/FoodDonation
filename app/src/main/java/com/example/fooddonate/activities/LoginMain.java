package com.example.fooddonate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.fooddonate.R;
import com.example.fooddonate.fragments.LogIn;
import com.example.fooddonate.fragments.RegisterUser;
import com.google.android.material.tabs.TabLayout;

public class LoginMain extends AppCompatActivity {

    TabLayout mytab;
    ViewPager mypager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        mytab = (TabLayout) findViewById(R.id.myTab);
        mypager = (ViewPager) findViewById(R.id.myPager);
        mypager.setAdapter(new MyOwnFragmentAdaptop(getSupportFragmentManager()));
        mytab.setupWithViewPager(mypager);
        mytab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mypager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class MyOwnFragmentAdaptop extends FragmentPagerAdapter
    {

        String data [] = {"Login","SignUp"} ;

        public MyOwnFragmentAdaptop(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (position==0)
            {
                return new LogIn();
            }
            if (position==1){
                return new RegisterUser();
            }
            return null;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data[position];
        }
    }

}