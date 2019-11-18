package com.example.storeapplication.Activities.Client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapplication.R;

public class AuthMenuActivity extends AppCompatActivity {

    Button register_button;
    Button login_button;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.user_selection_menu);

        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);

        register_button.setOnClickListener(view->ToRegister());
        login_button.setOnClickListener(view->ToLogin());
    }

    public void ToRegister()
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        this.startActivity(intent);
    }

    public void ToLogin()
    {
        Intent intent = new Intent(this,LoginActivity.class);
        this.startActivity(intent);
    }

}
