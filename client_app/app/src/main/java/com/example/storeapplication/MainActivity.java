package com.example.storeapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapplicaton.R;
import com.example.storeapplication.Activities.Client.AuthMenuActivity;
import com.example.storeapplication.Activities.Store.MainMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}

