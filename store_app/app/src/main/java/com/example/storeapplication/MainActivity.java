package com.example.storeapplication;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.io.IOException;

import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import Common.QR.QR;
import Common.QR.QRActivity;
import Common.QR.QRReadResultHandler;

public class MainActivity extends QRActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            HTTP.GetRequest("google.com", null, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    TextView text = findViewById(R.id.decode);
                    text.setText((String)result);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Handler(String data) {
        TextView text = findViewById(R.id.decode);
        text.setText(data);
    }

}

