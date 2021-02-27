package com.example.fooddonate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class Home extends Fragment{


    RecyclerView mainRecycleView;
    String title[],discretion[];
    MainRecycleViewAdaptor mainRecycleViewAdaptor;
    ViewGroup viewGroup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        title = getResources().getStringArray(R.array.Title);
        discretion = getResources().getStringArray(R.array.discretion);
        mainRecycleView = (RecyclerView) view.findViewById(R.id.rvMainHolder);
        mainRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewGroup= container;

        mainRecycleView.setAdapter(new MainRecycleViewAdaptor(title,discretion,container.getContext()) {
            @Override
            public void onRecyclerViewItemClicked(int position) {
//                Intent intent = new Intent(view.getContext(),ViewDonnerDetails.class);
//                intent.putExtra("title",mainRecycleViewAdaptor.title[position]);
//                startActivity(intent);
            }
        });
        return view;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {

//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//
        mainRecycleViewAdaptor = new MainRecycleViewAdaptor(title, discretion,viewGroup.getContext()) {
            @Override
            public void onRecyclerViewItemClicked(int position) {
                Intent intent = new Intent(view.getContext(),ViewDonnerDetails.class);
                intent.putExtra("title",mainRecycleViewAdaptor.title[position]);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

//    @Override
//    public void onRecyclerViewItemClicked(int position) {
//
//    }
}
