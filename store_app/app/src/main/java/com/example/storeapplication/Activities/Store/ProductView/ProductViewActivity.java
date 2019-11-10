package com.example.storeapplication.Activities.Store.ProductView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapplication.R;
import com.google.zxing.WriterException;

import Common.QR.QR;
import DataModels.Product;

public class ProductViewActivity extends AppCompatActivity {

    private Product product = null;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.product_view);

        Bundle b = getIntent().getExtras();
        product = (Product)b.getSerializable("product");

        ImageView qrCode = findViewById(R.id.productQRCode);
        Bitmap qrCodeData = null;
        try {
            qrCodeData = QR.GenerateQRCode(product.getRAWJSON());
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qrCode.setImageBitmap(qrCodeData);
    }

}
