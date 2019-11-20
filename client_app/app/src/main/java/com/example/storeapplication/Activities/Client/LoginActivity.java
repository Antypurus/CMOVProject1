package com.example.storeapplication.Activities.Client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapplicaton.R;

import DataModels.ClientSystem;

public class LoginActivity extends AppCompatActivity {

    private TextView username_view;
    private TextView password_view;
    private Button login_button;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.login);

        username_view = findViewById(R.id.username);
        password_view = findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(view->Login());
    }

    private boolean isLoginValid(String username, String password)
    {
        SharedPreferences preferences = getSharedPreferences(username,MODE_PRIVATE);
        if(!preferences.contains("password")) return false;
        String storedPassword = preferences.getString("password",null);
        if(storedPassword == null) return false;
        if(!storedPassword.equals(password)) return false;
        return true;
    }

    public void Login()
    {
        String username = username_view.getText().toString();
        String password = password_view.getText().toString();
        if(isLoginValid(username,password))
        {
            ClientSystem system = new ClientSystem(username);
            Intent intent = new Intent(this,MainMenuActivity.class);
            this.startActivity(intent);
        }else
        {
            //TODO(Tiago): Show Some Kind Of Error Message
        }
    }

}
