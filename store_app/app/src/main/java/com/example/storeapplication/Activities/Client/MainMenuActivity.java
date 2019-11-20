package com.example.storeapplication.Activities.Client;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import com.example.storeapplication.Activities.Client.CartView.CartActivity;
import com.example.storeapplication.R;

import Common.QR.QRActivity;
import DataModels.ClientSystem;
import DataModels.Product;

public class MainMenuActivity extends QRActivity {

    Button scan_button;
    Button cart_button;
    Button transaction_history_button;
    Button coupon_list_button;
    Button logout_button;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.client_menu);

        scan_button = findViewById(R.id.scan_button);
        cart_button = findViewById(R.id.cart_button);
        transaction_history_button = findViewById(R.id.past_transaction_button);
        coupon_list_button = findViewById(R.id.coupons_button);
        logout_button = findViewById(R.id.logout_button);

        scan_button.setOnClickListener(view->this.QRCodeReader.StartQRScanner(this, this));
        cart_button.setOnClickListener(view->CartHandler());
        transaction_history_button.setOnClickListener(view->TransactionHistoryHandler());
        coupon_list_button.setOnClickListener(view->CouponListHandler());
        logout_button.setOnClickListener(view->LogOutHandler());

    }

    @Override
    public void Handler(String data) {
        try {
            ClientSystem.GetSystem().GetCart().AddProduct(new Product(data,ClientSystem.GetSystem().GetServerKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CartHandler()
    {
        Intent intent = new Intent(this, CartActivity.class);
        this.startActivity(intent);
    }

    public void TransactionHistoryHandler()
    {
        //TODO
    }

    public void CouponListHandler()
    {
        //TODO
    }

    public void LogOutHandler()
    {
        //TODO
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
