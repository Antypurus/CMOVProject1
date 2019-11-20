package com.example.storeapplication.Activities.Client;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapplication.R;

public class CheckoutActivity extends AppCompatActivity {

    TextView cost;
    Switch use_accumulated;
    Switch use_coupon;
    ImageView checkout_code;
    Button checkout_button;

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

        //fetch coupon list

    }

}
