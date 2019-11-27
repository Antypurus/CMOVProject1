package com.example.storeapplication.Activities.Client;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapplicaton.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import Common.Constants;
import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import Common.QR.QR;
import Common.RSA;
import DataModels.ClientSystem;
import DataModels.Product;

public class CheckoutActivity extends AppCompatActivity {

    TextView cost;
    Switch use_accumulated;
    Switch use_coupon;
    ImageView checkout_code;
    Button checkout_button;

    ArrayList<String> vouchers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstaces)
    {
        super.onCreate(savedInstaces);
        setContentView(R.layout.checkout);

        cost = findViewById(R.id.total_cost);
        use_accumulated = findViewById(R.id.accumulated_use);
        use_coupon = findViewById(R.id.coupon_use);
        checkout_code = findViewById(R.id.checkout_code);
        checkout_button = findViewById(R.id.checkout_button);

        cost.setText(ClientSystem.GetSystem().GetCart().CalculateCartTotal()+"€");

        //fetch coupon list
        try {
            HashMap<String,String> headers = new HashMap<>();
            headers.put("user_id", ClientSystem.GetSystem().ClientUserID);
            HTTP.GetRequest(Constants.Coupon_List_Route, headers, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    // parse the voucher list data
                    try {
                        JSONObject resultJSON = new JSONObject((String) result);
                        String vouchers_string = resultJSON.getString("vouchers");
                        JSONArray vouchersJSON = new JSONArray(vouchers_string);
                        vouchers.clear();
                        for (int i = 0; i < vouchersJSON.length(); ++i) {
                            vouchers.add(vouchersJSON.getString(i));
                        }
                        checkout_button.setOnClickListener(view->GenerateCheckoutQRCode());

                        GenerateCheckoutQRCode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GenerateCheckoutQRCode()
    {
        try {
            JSONArray productsArray = new JSONArray();
            CopyOnWriteArrayList<Product> products = ClientSystem.GetSystem().GetCart().GetProducts();
            for (Product product : products) {
                productsArray.put(product.getProductID());
            }
            JSONObject body = new JSONObject();
            body.put("products", productsArray.toString());
            body.put("user_id", ClientSystem.GetSystem().ClientUserID);
            body.put("use_discount", String.valueOf(this.use_accumulated.isChecked()));
            String voucher = "";
            if (this.use_coupon.isChecked()) {
                if (this.vouchers.size() > 0) {
                    voucher = this.vouchers.get(0);
                    JSONObject voucherJSON = new JSONObject(voucher);
                    voucher = voucherJSON.getString("voucher_id");
                    body.put("voucher_id", voucher);
                }
            }
            String valueToSign = ClientSystem.GetSystem().ClientUserID + String.valueOf(this.use_accumulated.getShowText())+voucher;
            String sign = "";
            try {
                sign = RSA.Sign(valueToSign, ClientSystem.GetSystem().ClientUsername);
            } catch (Exception e) {
                e.printStackTrace();
            }
            body.put("sign", sign);
            String data = body.toString();

            Bitmap QRCode = QR.GenerateQRCode(data);
            this.checkout_code.setImageBitmap(QRCode);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
