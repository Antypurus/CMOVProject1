package com.example.storeapplication.Activities.Client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapplicaton.R;

import DataModels.ClientSystem;

public class AuthMenuActivity extends AppCompatActivity {

    Button register_button;
    Button login_button;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.user_selection_menu);

        register_button = findViewById(R.id.register);
        login_button = findViewById(R.id.login);

        register_button.setOnClickListener(view->ToRegister());
        login_button.setOnClickListener(view->ToLogin());

        SharedPreferences preferences = getSharedPreferences("Current_User",MODE_PRIVATE);
        String current = preferences.getString("Current_Username","");
        if(!current.equals(""))
        {
            ClientSystem system = new ClientSystem(current);
            Intent intent = new Intent(this,MainMenuActivity.class);
            this.startActivity(intent);
        }
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
