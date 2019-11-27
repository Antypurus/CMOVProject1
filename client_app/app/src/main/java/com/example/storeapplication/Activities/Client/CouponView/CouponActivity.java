package com.example.storeapplication.Activities.Client.CouponView;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.clientapplicaton.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Common.Constants;
import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import DataModels.ClientSystem;

public class CouponActivity extends AppCompatActivity {

    RecyclerView couponList;
    TextView accumulatedView;

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.coupons_list);

        couponList = findViewById(R.id.coupon_list);
        accumulatedView = findViewById(R.id.ammount_in_account);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        couponList.setLayoutManager(layoutManager);

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
                        int euro = (int) resultJSON.getInt("accumulated_euro");
                        int cent = (int) resultJSON.getInt("accumulated_cent");
                        float accumulated = euro + cent / 100.0f;

                        ArrayList<String> vouchers = new ArrayList<>();
                        JSONArray vouchersJSON = new JSONArray(vouchers_string);
                        for (int i = 0; i < vouchersJSON.length(); ++i) {
                            vouchers.add(vouchersJSON.getString(i));
                        }
                        CouponAdapter adapter = new CouponAdapter(vouchers);
                        couponList.setAdapter(adapter);
                        accumulatedView.setText(""+accumulated+"€");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
