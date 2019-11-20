package com.example.storeapplication.Activities.Client.CouponView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;

import java.util.ArrayList;

public class CouponAdapter extends  RecyclerView.Adapter<CouponAdapter.CouponViewHolder>{

    ArrayList<String> vouchers;

    public CouponAdapter(ArrayList<String> vouchers)
    {
        this.vouchers = vouchers;
    }

    public static class CouponViewHolder extends RecyclerView.ViewHolder
    {
        public TextView id;
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.coupon_id);
        }
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon,parent,false);
        CouponViewHolder holder = new CouponViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        holder.id.setText(this.vouchers.get(position));
    }

    @Override
    public int getItemCount() {
        return this.vouchers.size();
    }

}
