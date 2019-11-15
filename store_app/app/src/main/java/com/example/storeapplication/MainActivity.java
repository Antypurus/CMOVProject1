package com.example.storeapplication;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapplication.Activities.Client.RegisterActivity;
import com.example.storeapplication.Activities.Store.ProductList.ProductListActivity;

import Common.QR.QRActivity;
import DataModels.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

}

