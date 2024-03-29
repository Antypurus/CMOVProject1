package com.example.storeapplication.Activities.Store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storeapplication.Activities.Store.ProductList.ProductListActivity;
import com.example.storeapplication.R;

import Common.Constants;
import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import Common.QR.QRActivity;

public class MainMenu extends QRActivity {

    Button product_list;
    Button read_qr;
    TextView resultView;

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.store_menu);

        product_list = findViewById(R.id.product_list);
        read_qr = findViewById(R.id.checkout_client);
        resultView = findViewById(R.id.result);

        read_qr.setOnClickListener(view -> this.QRCodeReader.StartQRScanner(this, this));
        product_list.setOnClickListener(view->this.OpenProductList());
    }

    public void OpenProductList() {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    @Override
    public void Handler(String data) {
        Activity activity = this;
        HTTP.PostRequest(Constants.Checkout_Route, null, data, new HTTPResultHandler() {
            @Override
            public void Handler(Object result) {
                if ((boolean) result) {
                    resultView.setText("Transaction Complete");
                    Toast.makeText(activity,"Transaction Complete",Toast.LENGTH_LONG).show();
                } else {
                    resultView.setText("Transaction Failed");
                    Toast.makeText(activity,"Transaction Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
