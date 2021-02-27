package com.example.fooddonate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MainRecycleViewAdaptor  extends RecyclerView.Adapter<MainRecycleViewAdaptor.MainRecycleViewHolder> {
    String title[],discretion[];
    Context ctx;
    int img=R.drawable.profile;
//    RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public MainRecycleViewAdaptor(String[] title, String[] discretion, Context ct) {
        this.title = title;
        this.discretion = discretion;
        this.ctx = ct;
//        recyclerViewOnItemClickListener= (RecyclerViewOnItemClickListener) ct;
    }

    @NonNull
    @Override
    public MainRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(ctx);
        View myOwnView = myInflater.inflate(R.layout.activity_main_row,parent,false);
//        myOwnView.setOnClickListener(thi);
        return new MainRecycleViewHolder(myOwnView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecycleViewHolder holder, int position) {
        holder.t1.setText(title[position]);
        holder.t2.setText(discretion[position]);
        holder.imageView.setImageResource(img);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public abstract void onRecyclerViewItemClicked(int position);

    public class MainRecycleViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2;
        ImageView imageView;
//        RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
        public MainRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.txtTitle);
            t2 = (TextView) itemView.findViewById(R.id.txtDiscription);
            imageView = (ImageView) itemView.findViewById(R.id.imgProPic);
//            this.recyclerViewOnItemClickListener =recyclerViewOnItemClickListener;
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            recyclerViewOnItemClickListener.onRecyclerViewItemClicked(getAdapterPosition());
//        }
    }
//    interface RecyclerViewOnItemClickListener {
//        void onRecyclerViewItemClicked(int position);
//    }
}
