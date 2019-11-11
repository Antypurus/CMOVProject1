package com.example.storeapplication;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import com.example.storeapplication.Activities.Client.RegisterActivity;
import com.example.storeapplication.Activities.Store.ProductList.ProductListActivity;

import Common.QR.QRActivity;

public class MainActivity extends QRActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void Handler(String data) {
        TextView text = findViewById(R.id.decode);
        text.setText(data);
    }

}

