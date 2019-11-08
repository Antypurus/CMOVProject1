package com.example.storeapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import Common.QR;
import Common.QRActivity;
import Common.QRReadResultHandler;
import Common.RSA;

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
        TextView text = findViewById(R.id.decode);
    }

    @Override
    public void Handler(String data) {
        TextView text = findViewById(R.id.decode);
        text.setText(data);
    }
}

