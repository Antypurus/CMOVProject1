package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapplication.R;

import java.io.IOException;
import java.util.HashMap;

import Common.Constants;
import Common.HTTP.HTTP;
import Common.HTTP.HTTPResultHandler;
import DataModels.ClientSystem;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView transactions;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.transaction_history);

        this.transactions = findViewById(R.id.transactions);

        try {
            HashMap<String,String> headers = new HashMap<>();
            headers.put("user_id", ClientSystem.GetSystem().ClientUserID);
            HTTP.GetRequest(Constants.Transaction_History_Route, headers, new HTTPResultHandler() {
                @Override
                public void Handler(Object result) {
                    // parse response
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
