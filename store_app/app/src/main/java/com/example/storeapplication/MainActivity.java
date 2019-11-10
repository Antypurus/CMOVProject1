package com.example.storeapplication;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import com.example.storeapplication.Activities.Store.ProductList.ProductListActivity;

import Common.QR.QRActivity;

public class MainActivity extends QRActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
        /*
        ImageView image = (ImageView) findViewById(R.id.image);
        try {
            image.setImageBitmap(QR.GenerateQRCode("testing"));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        final Activity current = this;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeReader.StartQRScanner(current, (QRReadResultHandler) current);
            }
        });

        try {
            RSA.GeneratePublicPrivateKeyPair(this);
            HashMap<String, String> body = new HashMap<>();
            body.put("name", "name");
            body.put("username", "username");
            body.put("password", "password");
            body.put("credit_card_no", "1234");
            body.put("public_key", RSA.getPublicKey());
            HTTP.PostRequest("10.0.0.5/register", null, body, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    try {
                        JSONObject response = new JSONObject((String) result);
                        TextView text = findViewById(R.id.decode);
                        text.setText(response.getString("server_key"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void Handler(String data) {
        TextView text = findViewById(R.id.decode);
        text.setText(data);
    }

}

