package com.example.storeapplication.Activities.Client.TransactionHistoryView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapplication.R;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView transactions;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.transaction_history);

        this.transactions = findViewById(R.id.transactions);
    }

}
