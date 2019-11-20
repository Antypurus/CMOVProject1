package com.example.storeapplication.Activities.Store.ProductList;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;
import com.example.storeapplication.Activities.Store.ProductView.ProductViewActivity;

import java.util.ArrayList;

import DataModels.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListHolder>{

    ArrayList<Product> products;

    public static class ProductListHolder extends RecyclerView.ViewHolder
    {
        public ConstraintLayout layout;

        public TextView productName;
        public TextView productPrice;
        public Product product;

        ProductListHolder(@NonNull ConstraintLayout layout) {
            super(layout);

            this.layout = layout;
            this.productName = this.layout.findViewById(R.id.productName);
            this.productPrice = this.layout.findViewById(R.id.productPrice);
        }

    }

    ProductListAdapter(ArrayList<Product> products)
    {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_product,parent,false);
        ProductListHolder holder = new ProductListHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductListHolder holder, int position) {
        holder.product = products.get(position);
        float price = products.get(position).getPriceEuro() + products.get(position).getPriceCent()/100.0f;
        holder.productPrice.setText(""+price+"€");
        holder.productName.setText(products.get(position).getProductName());
        holder.layout.setOnClickListener(view -> goToProduct(holder.product,holder.layout));
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public void goToProduct(Product product, View view)
    {
        Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
        intent.putExtra("product",product);
        view.getContext().startActivity(intent);
    }

}
