package com.example.storeapplication.Activities.Client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapplication.Activities.Store.ProductView.ProductViewActivity;
import com.example.storeapplication.R;

import org.json.JSONObject;

import java.util.HashMap;

import Common.Constants;
import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import Common.RSA;

public class RegisterActivity extends AppCompatActivity {

    private TextView name;
    private TextView username;
    private TextView password;
    private TextView credit_card;
    private Button register_button;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.register_view);

        this.name = findViewById(R.id.name);
        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        this.credit_card = findViewById(R.id.card_number);
        this.register_button = findViewById(R.id.register_button);

        this.register_button.setOnClickListener(view->register());

    }

    private void SaveUserData(SharedPreferences preferences,String username, String password, String Key)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password",password);
        editor.putString("username",username);
        editor.putString("Key",Key);
        editor.commit();
    }

    private void ToMainMenu()
    {
        Intent intent = new Intent(this, MainMenuActivity.class);
        this.startActivity(intent);
    }

    public void register()
    {
        try {
            RSA.GeneratePublicPrivateKeyPair(this);
            HashMap<String, String> body = new HashMap<>();
            String nameS = this.name.getText().toString();
            String usernameS = this.username.getText().toString();
            String passwordS = this.password.getText().toString();
            String credit_card_noS = this.credit_card.getText().toString();
            body.put("name", nameS);
            body.put("username", usernameS);
            body.put("password", passwordS);
            body.put("credit_card_no", credit_card_noS);
            body.put("public_key", RSA.getPublicKey());
            SharedPreferences sharedPreferences = this.getSharedPreferences(usernameS, MODE_PRIVATE);
            HTTP.PostRequest(Constants.Register_Route, null, body, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    try {
                        JSONObject response = new JSONObject((String) result);
                        String key = response.getString("server_key");
                        SaveUserData(sharedPreferences,usernameS,passwordS,key);
                        ToMainMenu();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
