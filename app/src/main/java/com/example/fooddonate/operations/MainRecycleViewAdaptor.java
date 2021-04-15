package com.example.fooddonate.operations;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fooddonate.R;
import com.example.fooddonate.activities.ViewDonnerDetails;
import java.util.ArrayList;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainRecycleViewAdaptor  extends RecyclerView.Adapter<MainRecycleViewAdaptor.MainRecycleViewHolder> {
    String title[],discretion[];
//    int[] foodCat = {R.drawable.ic_non_veg, R.drawable.ic_veg};
    Context ctx;
    ArrayList<String> name,foodname,address,foodType,noOfPacks,mobileNo,emailId;

    int img= R.drawable.profile;
    Button btnCancel,btnRequest;

//    public MainRecycleViewAdaptor(String[] title, String[] discretion, Context ct) {
//        this.title = title;
//        this.discretion = discretion;
//        this.ctx = ct;
//    }

    public MainRecycleViewAdaptor(Context ctx, ArrayList<String> name, ArrayList<String> foodname, ArrayList<String> address, ArrayList<String> foodType, ArrayList<String> noOfPacks,ArrayList<String> mobileNo,ArrayList<String> emailId) {
        this.ctx = ctx;
        this.name = name;
        this.foodname = foodname;
        this.address = address;
        this.foodType = foodType;
        this.noOfPacks = noOfPacks;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
    }

    @NonNull
    @Override
    public MainRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(ctx);
        View myOwnView = myInflater.inflate(R.layout.activity_main_row,parent,false);
        return new MainRecycleViewHolder(myOwnView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecycleViewHolder holder, int position) {
        holder.t1.setText(name.get(position));
//        holder.t1.setText("Raju");
        holder.t2.setText(address.get(position));
        holder.imageView.setImageResource(img);
        if (foodType.get(position).equals("Veg"))
        {
            holder.foodCategory.setImageResource(R.drawable.ic_veg);
        }
        if (foodType.get(position).equals("Non-Veg")){
            holder.foodCategory.setImageResource(R.drawable.ic_non_veg);
        }
        if (foodType.get(position).equals("Veg & Non-veg"))
        {
            holder.foodCategory.setImageResource(R.drawable.ic_non_veg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "item view clicked", Toast.LENGTH_LONG).show();
                Log.i("info","item view clicked");
                Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.dialog_donation_details);
                TextView tvfoodName = dialog.findViewById(R.id.tvFoodName);
                tvfoodName.setText(foodname.get(position));
                TextView tvAddress = dialog.findViewById(R.id.tvAddress);
                tvAddress.setText(address.get(position));
                TextView tvFoodCat = dialog.findViewById(R.id.tvFoodCategory);
                tvFoodCat.setText(foodType.get(position));
                TextView tvFoodPacks = dialog.findViewById(R.id.tvFoodPack);
                tvFoodPacks.setText(noOfPacks.get(position));
                btnRequest = dialog.findViewById(R.id.btnRequest);
                btnCancel = dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx, "Request send Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        SendMail sendMail = new SendMail(ctx, emailId.get(position)+"", "Anurag");
                        sendMail.execute();
                    }
                });
                dialog.show();
            }
        });
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + mobileNo.get(position));
                ctx.getApplicationContext().startActivity(new Intent(Intent.ACTION_DIAL,u).addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx.getApplicationContext(), ViewDonnerDetails.class);
                intent.putExtra("Name",name.get(position));
                intent.putExtra("profilePic",img);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                ctx.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class MainRecycleViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2;
        ImageView imageView,btnCall,foodCategory;
        public MainRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.txtTitle);
            t2 = (TextView) itemView.findViewById(R.id.txtDiscription);
            imageView = (ImageView) itemView.findViewById(R.id.imgProPic);
            btnCall =  itemView.findViewById(R.id.btnCall);
            foodCategory = (ImageView) itemView.findViewById(R.id.ivFoodCategory);
        }
    }
}
