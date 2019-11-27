package com.example.storeapplication.Activities.Client.CartView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapplicaton.R;
import com.example.storeapplication.Activities.Client.CheckoutActivity;

import DataModels.ClientSystem;

public class CartActivity extends AppCompatActivity {

    RecyclerView product_list;
    TextView product_total;
    Button checkout_button;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.cart);

        product_list = findViewById(R.id.product_list);
        product_total = findViewById(R.id.current_total);
        checkout_button = findViewById(R.id.checkout_button);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        product_list.setLayoutManager(layout);
        product_list.setAdapter(new CartAdapter(ClientSystem.GetSystem().GetCart().GetProducts(),this));

        this.RecalculateTotal();

        checkout_button.setOnClickListener(view->GoToCheckout());

    }

    private void GoToCheckout()
    {
        Intent intent = new Intent(this, CheckoutActivity.class);
        this.startActivity(intent);
    }

    public void RecalculateTotal()
    {
        product_total.setText(ClientSystem.GetSystem().GetCart().CalculateCartTotal()+"€");
    }


}
