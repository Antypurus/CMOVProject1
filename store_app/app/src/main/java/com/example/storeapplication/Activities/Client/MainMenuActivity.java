package com.example.storeapplication.Activities.Client;

import android.os.Bundle;

import com.example.storeapplication.R;

import Common.QR.QRActivity;

public class MainMenuActivity extends QRActivity {

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.client_menu);
    }

    @Override
    public void Handler(String data) {

    }

}
