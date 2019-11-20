package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;

import java.util.ArrayList;

import DataModels.Product;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private ArrayList<Product> products;

    public TransactionAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
        }
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_product,parent,false);
        TransactionViewHolder holder = new TransactionViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Product product = this.products.get(position);
        holder.name.setText(product.getProductName());
        float price = product.getPriceEuro() + product.getPriceCent() / 100.0f;
        holder.price.setText("" + price);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
