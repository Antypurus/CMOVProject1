package com.example.storeapplication.Activities.Client.CouponView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapplication.R;

public class CouponAdapter extends  RecyclerView.Adapter<CouponAdapter.CouponViewHolder>{

    public static class CouponViewHolder extends RecyclerView.ViewHolder
    {
        TextView id;
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.coupon_id);
        }
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
