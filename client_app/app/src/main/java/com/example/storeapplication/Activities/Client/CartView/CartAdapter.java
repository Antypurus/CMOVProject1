package com.example.storeapplication.Activities.Client.CartView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;
import com.example.storeapplication.Activities.Store.ProductView.ProductViewActivity;

import java.util.concurrent.CopyOnWriteArrayList;

import DataModels.ClientSystem;
import DataModels.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private CopyOnWriteArrayList<Product> products;

    public CartAdapter(CopyOnWriteArrayList<Product> products)
    {
        this.products = products;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder
    {
        public Product product;
        public TextView product_name;
        public TextView product_price;
        public Button remove_button;

        public CartViewHolder(View view) {
            super(view);

            product_name = view.findViewById(R.id.product_name);
            product_price = view.findViewById(R.id.product_price);
            remove_button = view.findViewById(R.id.remove_button);

            remove_button.setOnClickListener( view1 -> this.RemoveButtonHandler());
            view.setOnClickListener(view1 -> OpenProduct());
        }

        private void RemoveButtonHandler()
        {
            ClientSystem.GetSystem().GetCart().RemoveProduct(this.product);
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(),products.size());
        }

        private void OpenProduct()
        {
            Intent intent = new Intent(this.itemView.getContext(), ProductViewActivity.class);
            intent.putExtra("product",product);
            this.itemView.getContext().startActivity(intent);
        }

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product,parent,false);
        CartViewHolder holder = new CartViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.product_name.setText(products.get(position).getProductName());
        double price = products.get(position).getPriceEuro() + products.get(position).getPriceCent()/100;
        String priceString = price+"€";
        holder.product_price.setText(priceString);
        holder.product = products.get(position);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
